package com.businessassistantbcn.opendata.helper;

import com.businessassistantbcn.opendata.proxy.HttpProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HttpProxyTest {
	
	public static final Logger log = LoggerFactory.getLogger(HttpProxyTest.class);

    @Autowired
    private Environment env;
    @Autowired
    private HttpProxy httpClientHelper;

    @Test
    public void getRequestDataTest(){

    }
    }
