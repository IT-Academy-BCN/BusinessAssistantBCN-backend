package com.businessassistantbcn.gencat.helper.argumentprovider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class CcaeDeserializerArgumentProvider implements ArgumentsProvider {

    private static final String JSON_FILENAME_CCAE_MISSING_DATA = "json/twoCcaeDataMissingData.json";
    private static final String JSON_FILENAME_WRONG_JSONFORMAT = "json/wrongJSONFormatData.json";
    private static final String JSON_FILENAME_CCAE_MISSING_FIELD = "json/twoCcaeDataMissingField.json";
    private static final String JSON_FILENAME_CCAE_MISSING_META = "json/twoCcaeDataMissingMeta.json";
    private static final String JSON_FILENAME_CCAE_MISSING_VIEW = "json/twoCcaeDataMissingView.json";
    private static final String JSON_FILENAME_CCAE_MISSING_COLUMNS = "json/twoCcaeDataMissingColumns.json";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws URISyntaxException, IOException {

        Path path1 = Paths.get(Objects.requireNonNull(CcaeDeserializerArgumentProvider.class.getClassLoader().getResource(JSON_FILENAME_CCAE_MISSING_DATA)).toURI());
        String ccaeMissingDataString = Files.readAllLines(path1, StandardCharsets.UTF_8).get(0);

        Path path2 = Paths.get(Objects.requireNonNull(CcaeDeserializerArgumentProvider.class.getClassLoader().getResource(JSON_FILENAME_WRONG_JSONFORMAT)).toURI());
        String ccaeWrongFormatString = Files.readAllLines(path2, StandardCharsets.UTF_8).get(0);

        Path path3 = Paths.get(Objects.requireNonNull(CcaeDeserializerArgumentProvider.class.getClassLoader().getResource(JSON_FILENAME_CCAE_MISSING_FIELD)).toURI());
        String ccaeMissingFieldString = Files.readAllLines(path3, StandardCharsets.UTF_8).get(0);

        Path path4 = Paths.get(Objects.requireNonNull(CcaeDeserializerArgumentProvider.class.getClassLoader().getResource(JSON_FILENAME_CCAE_MISSING_META)).toURI());
        String ccaeMissingMetaString = Files.readAllLines(path4, StandardCharsets.UTF_8).get(0);

        Path path5 = Paths.get(Objects.requireNonNull(CcaeDeserializerArgumentProvider.class.getClassLoader().getResource(JSON_FILENAME_CCAE_MISSING_VIEW)).toURI());
        String ccaeMissingViewString = Files.readAllLines(path5, StandardCharsets.UTF_8).get(0);

        Path path6 = Paths.get(Objects.requireNonNull(CcaeDeserializerArgumentProvider.class.getClassLoader().getResource(JSON_FILENAME_CCAE_MISSING_COLUMNS)).toURI());
        String ccaeMissingColumnsString = Files.readAllLines(path6, StandardCharsets.UTF_8).get(0);


        return Stream.of(
                Arguments.of(ccaeMissingDataString, "Field 'data' was not found"),
                Arguments.of(ccaeWrongFormatString, "The object must be an instance of LinkedHashMap"),
                Arguments.of(ccaeMissingFieldString, "JSON field not found"),
                Arguments.of(ccaeMissingMetaString, "Field 'meta' was not found"),
                Arguments.of(ccaeMissingViewString, "Field 'view' was not found"),
                Arguments.of(ccaeMissingColumnsString, "Field 'columns' was not found")
        );
    }
}
