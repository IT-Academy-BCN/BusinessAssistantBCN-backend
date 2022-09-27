package com.businessassistantbcn.gencat.helper;

import com.businessassistantbcn.gencat.dto.input.CcaeDto;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CcaeDeserializer extends StdDeserializer<List<CcaeDto>> {

    public CcaeDeserializer() {
        this(null);
    }

    protected CcaeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<CcaeDto> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {

        JsonNode productNode = jp.getCodec().readTree(jp);
        JsonNode dataArray = productNode.get("data");
        List<CcaeDto> result = new ArrayList<CcaeDto>();
        String id, name, assetType ="";

        for(int i=0; i<dataArray.size(); i++){
            id = dataArray.get(i).get(0).toString();
            name = dataArray.get(i).get(1).toString();
            assetType = dataArray.get(i).get(2).toString();
            result.add(new CcaeDto(id, name, assetType));
        }
        return result;
    }
}
