package com.businessassistantbcn.gencat.adapters;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.output.RaiscResponseDto;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Repository
public class GencatDataAdapter extends GencatJsonDecryptor implements DataSourceAdapter {

    private final HttpProxy proxy;

    private final PropertiesConfig config;

    @Autowired
    public GencatDataAdapter(HttpProxy proxy, PropertiesConfig config, ObjectMapper objectMapper) {
        super(objectMapper);
        this.proxy = proxy;
        this.config = config;
    }

    @Override
    public Flux<RaiscResponseDto> findAllRaisc() {
        URL url;
        try {
            url = obtainUrl();
        } catch (MalformedURLException e) {
            return Flux.error(new RuntimeException("URL in config malformed")); //low probability
        }
        Mono<String> jsonMono = obtainGencatJsonString(url); //errors captured with circuitbreaker on service
        return jsonMono
                .switchIfEmpty(Mono.error(new IOException("Gencat it's not providing a json")))
                .flatMap(json -> { //map executed only if no error has been propagated before
                    List<RaiscResponseDto> dataForService;
                    try {
                        dataForService = mapAllRaiscsWantedInfoFromGencat(json); //inherited method
                        //method for mapping can throw exceptions if the structure changes
                        return Mono.just(dataForService);
                    } catch (IOException e) {
                        String errorMessage = "Provided Gencat Json has changed data's structure";
                        return Mono.error(new IOException(errorMessage));
                    }
                }) //Mono<List<RaiscResponeDto>> or mono.error
                .flatMapMany(Flux::fromIterable);
    }

    private Mono<String> obtainGencatJsonString(URL url){
        return proxy.getRequestData(url, String.class); //can throw exception due connection problem
    }

    private URL obtainUrl() throws MalformedURLException {
        return new URL(config.getDs_scopes());//can throw exception due malformed
    }
}
