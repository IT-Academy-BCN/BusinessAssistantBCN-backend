package com.businessassistantbcn.gencat.helper;

import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import com.businessassistantbcn.gencat.dto.io.CodeInfoDto;
import com.businessassistantbcn.gencat.exception.IncorrectJsonFormatException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class CcaeDeserializer {

    public List<CcaeDto> deserialize(Object data) {
        CcaeDto ccaeDto;
        CodeInfoDto codeInfoDto;
        List<CcaeDto> listCcae = new ArrayList<>();

        if(data instanceof LinkedHashMap inputData) {
            ArrayList dataAdsArray = (ArrayList) inputData.get("data");
            if(dataAdsArray !=null){
                for (Object o : dataAdsArray) {
                    ArrayList element = (ArrayList) o;
                    codeInfoDto = new CodeInfoDto(
                            element.get(8).toString(), // Code ID
                            element.get(10).toString()); // Description
                    ccaeDto = new CcaeDto(
                            element.get(1).toString(),//'id' field
                            element.get(9).toString(),//'type'
                            codeInfoDto);//'Code information'
                    listCcae.add(ccaeDto);
                }
                return listCcae;
            }else {
                throw new IncorrectJsonFormatException("Field 'data' does not found");
            }

        }else {
            throw new IncorrectJsonFormatException("The object must be a instance of LinkedHashMap");
        }

    }
}
