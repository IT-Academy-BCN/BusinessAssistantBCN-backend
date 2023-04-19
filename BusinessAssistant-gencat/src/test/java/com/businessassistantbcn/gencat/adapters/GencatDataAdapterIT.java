package com.businessassistantbcn.gencat.adapters;

import com.businessassistantbcn.gencat.dto.output.RaiscResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GencatDataAdapterIT {

    @Autowired
    private DataSourceAdapter adapter;

    @Test
    @DisplayName("Test connection Gencat RAISC API and check few data if correctly mapped")
    void findAllRaiscTest(){
        Flux<RaiscResponseDto> sourceData = adapter.findAllRaisc();
        StepVerifier.create(sourceData)
                //we don't know how many, neither order, neither the expected values
                //-> expect a few + print it + compare to data in api
                .assertNext(raiscResponseDto -> {
                    assertNotNull(raiscResponseDto);
                    System.out.println(raiscResponseDto);
                })
                .consumeNextWith(System.out::println)
                .thenCancel()
                .verify();
    }


}
