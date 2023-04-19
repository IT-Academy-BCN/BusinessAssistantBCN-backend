package com.businessassistantbcn.gencat.utils.json.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.io.IOException;

public class JsonDeserializerUtils {

    private String json;

    private ObjectMapper mapper;

    public JsonDeserializerUtils(ObjectMapper mapper) {
        Assert.notNull(mapper, "provided ObjectMapper must be not null");
        this.mapper = mapper;
    }

    public JsonNode getNodeByName(String fieldName) throws JsonProcessingException {
        Assert.notNull(fieldName, "Field name can't be null");
        return mapper.readTree(json).get(fieldName);
    }

    public String[] parseNonNullNodeArrayToStringArray(JsonNode arrayNode) throws IOException {
        Assert.notNull(arrayNode, "Node can't be null");
        return mapper.readerFor(String[].class).readValue(arrayNode);
    }

    public void setJson(String json) {
        Assert.notNull(json, "json to deserialize must be not null");
        this.json = json;
    }
}
