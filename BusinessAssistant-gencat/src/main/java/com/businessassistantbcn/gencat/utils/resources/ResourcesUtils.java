package com.businessassistantbcn.gencat.utils.resources;

import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

public class ResourcesUtils {

    private Resource resource;

    public ResourcesUtils(Resource resource) {
        this.resource = resource;
    }

    /**
     * Parses a Resource to an equivalent String
     * Obs: Resource can be injected in client. Example:
     *      @Value("classpath:json/allRaiscData.json")
     *      private Resource allGencatData;
     * @return the rsource as String
     */
    public String asString() {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
