package com.businessassistantbcn.gencat.helper;

import com.businessassistantbcn.gencat.dto.input.CcaeDto;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CcaeDeserializer {


    public List<CcaeDto> deserialize(ArrayList<?> data) {
        CcaeDto ccaeDto;
        List<CcaeDto> listCcae = new ArrayList<CcaeDto>();

        for(int i=0; i<data.size(); i++){
            ArrayList element = (ArrayList) data.get(i);
            ccaeDto = new CcaeDto(
                    element.get(0).toString(),//'id' field
                    element.get(1).toString(),//'name'
                    element.get(2).toString());//'assetType'
            listCcae.add(ccaeDto);
        }
        return listCcae;
    }
}
