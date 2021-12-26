package com.businessassistantbcn.opendata.helper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HttpClientHelperTest {
	
	public static final Logger log = LoggerFactory.getLogger(HttpClientHelperTest.class);

    @Autowired
    private Environment env;
    @Autowired
    private HttpClientHelper httpClientHelper;

    @Test
    public void getStringRootTest() {

        try {
            URL url = new URL(env.getProperty("ds_test"));
            assertEquals(httpClientHelper.getStringRoot(url) , "");

        } catch (MalformedURLException e) {
        	log.error("exception:", e);
        }

    }

       // @Test
        public void getJsonRootTest() {
        }

        //@Test
        public void getObjectRootTest (){

        }
    }
