package com.businessassistantbcn.opendata;

import com.businessassistantbcn.opendata.configuration.MyConfig;
import com.businessassistantbcn.opendata.entity.GranEstabliment;
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
        List<GranEstabliment> allGranEstabliments = communication.getAllGranEstabliments();

        // here we print out all the GranEstabliments
        System.out.println(allGranEstabliments);

        // here we can get GranEstabliment by id
        GranEstabliment GranEstablimentById = communication.getGranEstabliment(1);

        // here we print out the GranEstabliment by id
        System.out.println(GranEstablimentById);
    }

}