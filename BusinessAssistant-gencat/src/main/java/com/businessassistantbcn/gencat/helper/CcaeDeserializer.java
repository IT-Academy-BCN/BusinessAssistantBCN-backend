package com.businessassistantbcn.gencat.helper;

import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import com.businessassistantbcn.gencat.dto.io.CodeInfoDto;
import com.businessassistantbcn.gencat.exception.ExpectedJSONFieldNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.IntStream;

@Component
public class CcaeDeserializer {

    public List<CcaeDto> deserialize(Object data) {

        List<CcaeDto> listCcae = new ArrayList<>();

        LinkedHashMap<?, ?> inputData = Optional.ofNullable(data)
                .filter(LinkedHashMap.class::isInstance)
                .map(LinkedHashMap.class::cast)
                .orElseThrow(() -> new ExpectedJSONFieldNotFoundException("The object must be an instance of LinkedHashMap"));

        ArrayList<?> dataAdsArray = Optional.ofNullable(inputData.get("data"))
                .map(ArrayList.class::cast)
                .orElseThrow(() -> new ExpectedJSONFieldNotFoundException("Field 'data' was not found"));

        //Get field names from metadata
        Map<Object, Integer> fieldNames = metaDataReader(inputData);

        dataAdsArray.forEach(object -> {
            ArrayList<?> element = Optional.of(object)
                    .map(ArrayList.class::cast)
                    .orElseGet(ArrayList::new);
            try {
                CodeInfoDto codeInfoDto = new CodeInfoDto(
                        element.get(fieldNames.get("codi")).toString(), //Code ID ("codi")
                        element.get(fieldNames.get("descripci")).toString()); //Description ("descripci")
                CcaeDto ccaeDto = new CcaeDto(
                        element.get(fieldNames.get(":id")).toString(), //'id' field (":id")
                        element.get(fieldNames.get("tipus")).toString(), //'type' ("tipus")
                        codeInfoDto); //'Code information'
                listCcae.add(ccaeDto);
            } catch (NullPointerException npe) {
                throw new ExpectedJSONFieldNotFoundException("JSON field not found");
            }
        });

        return listCcae;
    }

    private Map<Object, Integer> metaDataReader(LinkedHashMap<?, ?> inputData) {
        //Returns map with id of each field name and its position (index)

        HashMap<?, ?> metaDataWrapped = Optional.ofNullable(inputData.get("meta")).map(HashMap.class::cast)
                .orElseThrow(() -> new ExpectedJSONFieldNotFoundException("Field 'meta' was not found"));

        HashMap<?, ?> metaData = Optional.ofNullable(metaDataWrapped.get("view")).map(HashMap.class::cast)
                .orElseThrow(() -> new ExpectedJSONFieldNotFoundException("Field 'view' was not found"));

        ArrayList<?> columns = Optional.ofNullable(metaData.get("columns")).map(ArrayList.class::cast)
                .orElseThrow(() -> new ExpectedJSONFieldNotFoundException("Field 'columns' was not found"));

        columns.forEach(column -> Optional.of(column).map(HashMap.class::cast).orElseGet(HashMap::new));

        Map<Object, Integer> fields = new HashMap<>();
        IntStream.range(0, columns.size()).forEach(index ->
                fields.put(
                        Optional.of(columns.get(index))
                                .map(HashMap.class::cast)
                                .orElseGet(HashMap::new)
                                .get("fieldName"),
                        index));

        return fields;
    }
}
