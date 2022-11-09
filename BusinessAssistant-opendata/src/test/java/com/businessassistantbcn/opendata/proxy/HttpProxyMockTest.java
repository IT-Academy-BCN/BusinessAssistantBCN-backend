package com.businessassistantbcn.opendata.proxy;

import com.businessassistantbcn.opendata.dto.input.bigmalls.BigMallsDto;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class HttpProxyMockTest {

    @Autowired
    private HttpProxy httpProxy;

    public static MockWebServer mockWebServer;
    private URL url;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void initialize() throws MalformedURLException {
        this.url = new URL("http://api/big-mallsData.json");
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getRequestDataTest() {
        BigMallsDto[] bigMalls = httpProxy.getRequestData(url, BigMallsDto[].class).block();

        assertEquals(27, bigMalls.length);
        assertEquals(43326347, bigMalls[0].getClassifications_data().get(0).getId());
    }
}
