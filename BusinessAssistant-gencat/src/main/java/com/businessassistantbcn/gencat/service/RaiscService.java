package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.output.*;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RaiscService {

    private static final Logger log = LoggerFactory.getLogger(RaiscService.class);

    @Autowired
    private GenericResultDto<RaiscResponseDto> genericResultDto;
    @Autowired
    Environment environment;

    @Autowired
    private RaiscResponseDto raiscResponseDto;

    @Autowired
    private HttpProxy httpProxy;

    public Mono<GenericResultDto<RaiscResponseDto>> getPageByRaiscYear(int offset, int limit, String year) {
        return getRaiscDefaultPage();
    }

    private Mono<GenericResultDto<RaiscResponseDto>> getRaiscDefaultPage() {
        genericResultDto.setInfo(0, 0, 0, new RaiscResponseDto[0]);
        return Mono.just(genericResultDto);
    }

    public Mono<GenericResultDto<ScopeDto>> getScopes(int offset, int limit) throws IOException {
        return getScopesByPage(offset, limit);
    }


    private Mono<GenericResultDto<ScopeDto>> getScopesByPage(int offset, int limit) throws IOException {
        if(limit == -1){
            limit = 100;
            offset= 0;
        }
        String urlStr = environment.getProperty("url.ds_scopes") + "?limit=" + limit + "&offset=" + offset;
        List<ScopeDto> scopes = new ArrayList<>();
        int count = 0;
        HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        JSONObject json = new JSONObject(content.toString());
        JSONArray data = json.getJSONArray("data");

        for (int i = 0; i < limit; i++) {
            if(offset != 0){
                offset--;
            }else {
                JSONArray row = data.getJSONArray(i);
                String idScope = row.getString(35);
                String scope = row.getString(36);
                scopes.add(new ScopeDto(idScope, scope));
                count++;
            }
        }
        ScopeDto[] resultArray = scopes.toArray(new ScopeDto[0]);
        GenericResultDto<ScopeDto> genericResultDto = new GenericResultDto<>(offset, limit, count, resultArray);

        return Mono.just(genericResultDto);
    }


}





