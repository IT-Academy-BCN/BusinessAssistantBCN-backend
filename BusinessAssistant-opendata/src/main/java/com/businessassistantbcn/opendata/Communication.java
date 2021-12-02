package com.businessassistantbcn.opendata;

import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


// Using the object of this class and its methods we will communicate with the REST SERVICE, by other words, we will realize HTTP requests and get responses

@Component
public class Communication {

    @Autowired
    private RestTemplate restTemplate; // it is a dependency injection of the bean of RestTemlate class in this Communication class

    private final String URL = "https://opendata-ajuntament.barcelona.cat/data/es/dataset/grans-establiments";


    // This method will send HTTP request and get a list of all the gran establiments from the opendata REST SERVICE
    public List<LargeStablishmentsResponseDto> getAllLargeStablishments(){

        // ResponseEntity is a wrapper of the HTTP response, which represents the whole HTTP response: status code, headers, and body. As a result, we can use it to fully configure the HTTP response. If we want to use it, we have to return it from the endpoint; Spring takes care of the rest. ResponseEntity is a generic type.
        ResponseEntity<List<LargeStablishmentsResponseDto>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<LargeStablishmentsResponseDto>>() {}); // RestTemplate object is used to realize HTTP requests. The 3d parameter of the exchange() method is a ResponseEntity, which is used if you want to add a body to the HTTP request. But in this case we use HttpMethod.GET, and the body of the GET method is always empty. That's why we use null as a 3d parameter. The 4th parameter is ParameterizedTypeReference<>() which is a class helper, the goal of which is to transmit a generic type. In our case the generic type is a list of LargeStablishmentsResponseDto. So, using this whole line we send a request, and we get its result in the ResponseEntity

        // Here from the body of the ResponseEntity, we will get a payload (in computing and telecommunications, the payload is the part of transmitted data that is the actually intended message. The payload excludes headers or metadata, which are sent simply to facilitate message delivery.) In our case the payload is a list of all the GranEstabliments
        List<LargeStablishmentsResponseDto> allLargeStablishmentsResponseDtos = responseEntity.getBody();

        return allLargeStablishmentsResponseDtos;
    }


    // This method will get a concrete gran establiment by id from the opendata REST SERVICE. Here we use HTTP request, the result of which is getting a LargeStablishmentsResponseDto
    public LargeStablishmentsResponseDto getLargeStablishment(long id){

        // This operation we accomplish using the RestTemplate class helper as well. For this purpose I use the getForObject() method. The 1st parameter is our URL + id of the object. And the 2d parameter is what we expect to get as a response to this HTTP request. As we expect to get a LargeStablishmentsResponseDto, so we use Reflection of the LargeStablishmentsResponseDto.class
        LargeStablishmentsResponseDto largeStablishmentsResponseDto = restTemplate.getForObject(URL + "/" + id, LargeStablishmentsResponseDto.class);

        // here we return the gran establiment
        return largeStablishmentsResponseDto;
    }

}
