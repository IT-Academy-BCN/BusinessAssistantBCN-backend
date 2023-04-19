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
        return obtainGencatJsonString()
                .switchIfEmpty(Mono.error(new IOException("Gencat it's not providing a json")))
                .flatMap(json -> { //map executed only if no error has been propagated before
                    String errorMessage = "Provided Gencat Json has changed data's structure";
                    try {
                        List<RaiscResponseDto> serviceData = mapAllRaiscsWantedInfoFromGencat(json); //inherited method
                        return serviceData != null? Mono.just(serviceData) : Mono.error(new IOException(errorMessage));
                    } catch (IOException e) {
                        return Mono.error(new IOException(errorMessage));
                    }
                }) //Mono<List<RaiscResponeDto>> or mono.error
                .flatMapMany(Flux::fromIterable);
    }

    private Mono<String> obtainGencatJsonString(){
        try {
            URL url = new URL(config.getDs_scopes()); //can throw exception due malformed
            return proxy.getRequestData(url, String.class); //can throw exception due connection problem
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
