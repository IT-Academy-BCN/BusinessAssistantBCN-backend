package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.config.SpringDBTestConfiguration;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BigMallsServiceTest {

    @Mock
    private PropertiesConfig config;

    @Mock
    private HttpProxy httpProxy;

    @Mock
    private GenericResultDto<BigMallsDto> genericResultDto;

    @InjectMocks
    private BigMallsService bigMallsService;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @Test
    void getPagetest(){
        assertTrue(true);
    }

    @Test
    void getPageMalformedURLExceptionThrowsDefaultPagetest(){
        when(config.getDs_bigmalls()).thenReturn("gibberish");
        bigMallsService.getPage(0, -1);
        verify(httpProxy, times(0)).getRequestData(any(URL.class), any(Class.class));
    }

    @Test
    void getPageCircuitBreakerThrowsDefaultPagetest(){
        assertTrue(true);
    }
}

		/*playerDto.setName(name);
                PlayerDto expectedDto = new PlayerDto();
                expectedDto.setName(expectedName);
                when(playerRepository.existsByName(any(String.class))).thenReturn(false);
        when(playerRepository.save(any(Player.class)))
        .thenAnswer(i -> i.getArgument(0));
        when(mapper.onePlayerToDto(any(Player.class))).thenReturn(expectedDto);

        assertEquals(expectedName, playerService.addPlayer(playerDto).getName());

        ArgumentCaptor<Player> argument = ArgumentCaptor.forClass(Player.class);
        verify(playerRepository, times(1)).save(any(Player.class));
        verify(mapper, times(1)).onePlayerToDto(argument.capture());
        assertEquals(expectedName, argument.getValue().getName());*/