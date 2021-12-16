package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.SpringTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringTestConfiguration.class })
public class BigMallsServiceTest {

    @Test
    void test(){
        assertTrue(true);
    }
}
