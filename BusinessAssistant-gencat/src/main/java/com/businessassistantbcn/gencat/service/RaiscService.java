package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.adapters.DataSourceAdapter;
import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.output.ErrorDto;
import com.businessassistantbcn.gencat.dto.output.RaiscResponseDto;
import com.businessassistantbcn.gencat.dto.output.ResponseScopeDto;
import com.businessassistantbcn.gencat.helper.JsonHelper;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class RaiscService {

    @Autowired
    private DataSourceAdapter dataAdapter;

    @Autowired
    private GenericResultDto<RaiscResponseDto> genericResultDto;
    @Autowired
    private GenericResultDto<ResponseScopeDto> genericScopeDto;
    @Autowired
    private RaiscResponseDto raiscResponseDto;
    @Autowired
    private PropertiesConfig config;
    @Autowired
    HttpProxy httpProxy;

    public Mono<GenericResultDto<RaiscResponseDto>> getPageByRaiscYear(int offset, int limit, String year) {
        return getRaiscDefaultPage();
    }

    private Mono<GenericResultDto<RaiscResponseDto>> getRaiscDefaultPage() {
        genericResultDto.setInfo(0, 0, 0, new RaiscResponseDto[0]);
        return Mono.just(genericResultDto);
    }
    public Mono<List<ResponseScopeDto>> getScopes(int offset, int limit) throws MalformedURLException {
        // Hacer un GET a la URL que se encuentra en el application.yml
        Mono<String> response =  httpProxy.getRequestData(new URL(config.getDs_scopes()), String.class);
        // Extraer ResponseScopeDto de toda la data de un Flux.
        // .skip para saltar la data del offset y .take para limitar los resultados
        return response
                .map(content -> extractScopes(content))
                .flatMapMany(Flux::fromIterable)
                .distinct()
                .collectList()
                .map(scopes -> JsonHelper.filterDto(scopes.toArray(new ResponseScopeDto[scopes.size()]), offset, limit))
                .flatMapMany(Flux::fromArray)
                .collectList();
    }

    private static List<ResponseScopeDto> extractScopes(String content) {
        List<ResponseScopeDto> scopes = new ArrayList<>();
        // crear un Json de la data extraida
        org.json.JSONObject json = new JSONObject(content);
        JSONArray data = json.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONArray row = data.getJSONArray(i);
            // En este caso sé que es el 35 y 36 porque lo pone en RaiscResponseDto
            // que sería la data entera
            String idScope = row.getString(35);
            String scope = row.getString(36);
            scopes.add(new ResponseScopeDto(idScope, scope));
        }
        return scopes;
    }

    public Mono<GenericResultDto<Object>> getPageRaiscByScope(int offset, int limit, String idScope) {
        Flux<RaiscResponseDto> raiscFlux = dataAdapter.findAllRaisc();
        Predicate<RaiscResponseDto> filter = raisc -> raisc.getIdScope().equals(idScope);
        raiscFlux = doPage(raiscFlux, filter  , offset, limit);
        return mapToGenericUnkown(raiscFlux, offset, limit);
    }

    private Flux<RaiscResponseDto> doPage(Flux<RaiscResponseDto> raiscFlux,
                                          Predicate<RaiscResponseDto> raiscFilter,
                                          int offset,
                                          int limit){
        raiscFlux = doFilter(raiscFlux, raiscFilter);
        raiscFlux = excludeOffset(raiscFlux, offset);
        raiscFlux = excludeOutOfLimit(raiscFlux, limit);
        return raiscFlux;
    }

    private Flux<RaiscResponseDto> doFilter(Flux<RaiscResponseDto> raiscFlux, Predicate<RaiscResponseDto> raiscFilter) {
        return raiscFlux
                .filter(raiscFilter)
                //to exlude raisc/announcement in case data adapter provides a duplicated raisc response
                .distinct();
    }

    private Flux<RaiscResponseDto> excludeOffset(Flux<RaiscResponseDto> raiscFlux, int offset) {
        if(offset > 0){
            return raiscFlux.skip(offset);
        }else {
            return raiscFlux;
        }
    }

    private Flux<RaiscResponseDto> excludeOutOfLimit(Flux<RaiscResponseDto> raiscFlux, int limit) {
        if(limit >= 0){
            return raiscFlux.take(limit);
        }else{
            return raiscFlux;
        }
    }

    private Mono<GenericResultDto<Object>> mapToGenericUnkown(Flux<RaiscResponseDto> raiscFlux, int offset, int limit) {
        Mono<GenericResultDto<Object>> expectedResponse = mapToGenericResult(raiscFlux, offset, limit);
        return ifErrorMap(expectedResponse);
    }

    private Mono<GenericResultDto<Object>> mapToGenericResult(Flux<RaiscResponseDto> raiscFlux, int offset, int limit) {
        return raiscFlux
                .collectList() //Mono<List<RaiscResponseDto>>
                .map(raiscResponses -> {
                    GenericResultDto<Object> genricRaiscResponse = new GenericResultDto<>();
                    genricRaiscResponse.setInfo(offset, limit, raiscResponses.size(), raiscResponses.toArray(RaiscResponseDto[]::new));
                    return genricRaiscResponse;
                });
    }

    private Mono<GenericResultDto<Object>> ifErrorMap(Mono<GenericResultDto<Object>> expectedResponse) {
        return expectedResponse.onErrorResume(error -> {
            ErrorDto[] errorsDto = new ErrorDto[]{new ErrorDto(error.getMessage())};
            GenericResultDto<Object> errorResult = new GenericResultDto<>();
            errorResult.withErrors(errorsDto);
            return Mono.just(errorResult);
        });
    }
}
