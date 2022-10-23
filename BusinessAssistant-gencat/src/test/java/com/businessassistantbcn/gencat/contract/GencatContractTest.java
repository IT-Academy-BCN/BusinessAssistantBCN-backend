package com.businessassistantbcn.gencat.contract;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import com.businessassistantbcn.gencat.dto.io.CodeInfoDto;
import com.businessassistantbcn.gencat.service.CcaeService;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest
@PactTestFor(providerName = "apitest-gencat-provider", port = "8760")
class GencatContractTest {

    @Autowired
    private CcaeService ccaeService;

    @Pact(provider = "apitest_gencat_provider", consumer = "gencat_CcaeService")
    public RequestResponsePact serverUp(PactDslWithProvider builder) throws URISyntaxException, IOException {

        Path path = Paths.get(Objects.requireNonNull(GencatContractTest.class.getClassLoader().getResource("json/ccaeValidData.json")).toURI());
        String ccaeAsString = Files.readAllLines(path, StandardCharsets.UTF_8).get(0);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.given("Server up")
                .uponReceiving("Test interaction - Server up")
                .path("/flowers/test")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .headers(headers)
                .status(HttpStatus.OK.value()) //200
                .body(ccaeAsString, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                .toPact();
    }

    @Pact(provider = "apitest_gencat_provider", consumer = "gencat_CcaeService")
    public RequestResponsePact serverDown(PactDslWithProvider builder) {

        return builder.given("Server down")
                .uponReceiving("Test interaction - Server down")
                .path("/flowers/test")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()) //500
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "serverUp")
    void serverUpTest() throws IOException {

        List<CcaeDto> allData = new ArrayList<>();
        CcaeDto[] expected;

        CcaeDto ccaeDto1 = new CcaeDto();
        String id1 = "00000000-0000-0000-D7DC-CC770365D8FF";
        String type1 = "Secció";
        String idCodeInfo1 = "A";
        String codeDescription1 = "Agricultura, ramaderia, silvicultura i pesca";
        CodeInfoDto codeInfoDto1 = new CodeInfoDto(idCodeInfo1, codeDescription1);
        ccaeDto1.setId(id1);
        ccaeDto1.setType(type1);
        ccaeDto1.setCode(codeInfoDto1);
        allData.add(ccaeDto1);

        CcaeDto ccaeDto2 = new CcaeDto();
        String id2 = "00000000-0000-0000-2335-839767DDAEAB";
        String type2 = "Divisió";
        String idCodeInfo2 = "01";
        String codeDescription2 = "Agricultura, ramaderia, caça i activitats dels serveis que s'hi relacionen";
        CodeInfoDto codeInfoDto2 = new CodeInfoDto(idCodeInfo2, codeDescription2);
        ccaeDto2.setId(id2);
        ccaeDto2.setType(type2);
        ccaeDto2.setCode(codeInfoDto2);
        allData.add(ccaeDto2);

        expected = allData.toArray(CcaeDto[]::new);

        GenericResultDto<CcaeDto> actual = ccaeService.getPage(0, -1).block();

        assertThat(actual).isNotNull();
        assertThat(actual.getResults()[0]).usingRecursiveComparison().isEqualTo(expected[0]);
        assertThat(actual.getResults()[1]).usingRecursiveComparison().isEqualTo(expected[1]);
    }

    @Test
    @PactTestFor(pactMethod = "serverDown")
    void serverDownTest() throws IOException {

        GenericResultDto<CcaeDto> actual = ccaeService.getPage(0, -1).block();

        GenericResultDto<CcaeDto> expected = new GenericResultDto<>();
        expected.setInfo(0, -1, 0, new CcaeDto[0]);

        assertThat(actual).isNotNull();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
