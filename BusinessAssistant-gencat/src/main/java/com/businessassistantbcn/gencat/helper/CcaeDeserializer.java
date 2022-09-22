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

            System.out.println("*******" + id + " - " + name + " - " + assetType);

            result.add(new CcaeDto(id, name, assetType));

        }

        System.out.println("#####################################################################");

        for(int i=0; i<result.size(); i++){
            System.out.println("*******" + ((CcaeDto) result.get(i)).getName());

        }

        return result;

//****************************************************************************************


/*        AllCcaeDto allCcaeDto = new AllCcaeDto();

        CcaeDto ccaeDto = new CcaeDto();

        CodeInfoDto codeInfoDto = new CodeInfoDto();

        List<CcaeDto> ccaeDto1List = new ArrayList<>();

        JsonNode productNode = jp.getCodec().readTree(jp);

        JsonNode dataArray = productNode.get("data");

        if(dataArray != null) {



            if (dataArray.isArray()) {
                for (int i = 0; i < dataArray.size(); i++) {

                    ccaeDto.setId(productNode.get("data").get(i).get(1).textValue());
                    ccaeDto.setType(productNode.get("data").get(i).get(9).textValue());
                    codeInfoDto.setIdCcae(productNode.get("data").get(i).get(8).textValue());
                    codeInfoDto.setDescription(productNode.get("data").get(i).get(10).textValue());

                    ccaeDto.setCodeInfoDto(codeInfoDto);

                    ccaeDto1List.add(ccaeDto);
                }
            } else {
                System.out.println("There's to implement a exception");
            }

        }else {
            System.out.println("There's to implement a exception");
        }

        allCcaeDto.setAllCcae(ccaeDto1List);
        return allCcaeDto;*/
    }
}
