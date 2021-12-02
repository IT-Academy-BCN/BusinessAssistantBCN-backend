package com.businessassistantbcn.opendata;

import com.businessassistantbcn.opendata.configuration.MyConfig;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsResponseDto;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

// From within this app thanks to the REST API we can direct ourselves to the REST SERVICE, using HTTP requests, and get the results of these requests, and concretely the HTTP response
@SpringBootApplication
public class App {

    public static void main(String[] args) {

        // For the configuration purpose we use just the Java class, that's why we will get the context by the following way
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyConfig.class);


        // And now, by using the created context we will create the communication bean by the following way
        Communication communication = context.getBean("communication", Communication.class);

        // And here we call the method itself
        List<LargeStablishmentsResponseDto> allLargeStablishmentsResponseDtos = communication.getAllLargeStablishments();

        // here we print out all the GranEstabliments
        System.out.println(allLargeStablishmentsResponseDtos);

        // here we can get LargeStablishmentsResponseDto by id
        LargeStablishmentsResponseDto largeStablishmentsResponseDtoById = communication.getLargeStablishment(1);

        // here we print out the LargeStablishmentsResponseDto by id
        System.out.println(largeStablishmentsResponseDtoById);
    }

}