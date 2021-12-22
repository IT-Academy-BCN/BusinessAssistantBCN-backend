package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.config.SpringDBTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BigMallsServiceTest {

    @Test
    void test(){
        assertTrue(true);
    }
}
