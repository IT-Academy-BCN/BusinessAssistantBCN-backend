package com.businessassistantbcn.gencat.helper;

import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import com.businessassistantbcn.gencat.dto.io.CodeInfoDto;
import com.businessassistantbcn.gencat.exception.IncorrectJsonFormatException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CcaeDeserializer {

    public List<CcaeDto> deserialize(Object data) {
        List<CcaeDto> listCcae = new ArrayList<>();

        //TODO

        /**
         * - Verificar que inputData no está vacío y no es nulo (¿uso de Optional?)
         * - Verificamos existencia de campo data
         * - Casting a ArrayList
         * - etc
         */

        LinkedHashMap inputData = Optional.ofNullable(data)
                .filter(LinkedHashMap.class::isInstance)
                .map(LinkedHashMap.class::cast)
                .orElseThrow(() -> new IncorrectJsonFormatException("The object must be an instance of LinkedHashMap"));

        ArrayList dataAdsArray = Optional.of(inputData.get("data"))
                .map(ArrayList.class::cast)
                .orElseThrow(() -> new IncorrectJsonFormatException("Field 'data' was not found"));

        dataAdsArray.forEach(object -> {
            ArrayList element = Optional.of(object)
                    .map(ArrayList.class::cast)
                    .orElseThrow(() -> new IncorrectJsonFormatException("Malformed data"));
            CodeInfoDto codeInfoDto = new CodeInfoDto(
                    element.get(8).toString(), // Code ID
                    element.get(10).toString()); // Description
            CcaeDto ccaeDto = new CcaeDto(
                    element.get(1).toString(),//'id' field
                    element.get(9).toString(),//'type'
                    codeInfoDto);//'Code information'
            listCcae.add(ccaeDto);
        });

        return listCcae;
    }
}
