package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.output.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RaiscResponseDto raiscResponseDto;

    public Mono<GenericResultDto<RaiscResponseDto>> getPageByRaiscYear(int offset, int limit, String year) {
        return getRaiscDefaultPage();
    }

    private Mono<GenericResultDto<RaiscResponseDto>> getRaiscDefaultPage() {
        genericResultDto.setInfo(0, 0, 0, new RaiscResponseDto[0]);
        return Mono.just(genericResultDto);
    }

    public Mono<List<ScopeDto>> parseJsonFromUrl() throws IOException {
        String url = "https://analisi.transparenciacatalunya.cat/api/views/khxn-nv6a/rows.json";
        List<ScopeDto> scopes = new ArrayList<>();

        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
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
        for (int i = 0; i < data.length(); i++) {
            JSONArray row = data.getJSONArray(i);
            String idScope = row.getString(0);
            String scope = row.getString(1);
            scopes.add(new ScopeDto(idScope, scope));
        }

        return Mono.just(scopes);

    }



}
