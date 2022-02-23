package com.businessassistantbcn.mydata.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.businessassistantbcn.mydata.entities.Search;
import com.businessassistantbcn.mydata.service.MydataService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/businessassistantbcn/api/v1/mydata")
public class MydataController {
	
	MydataService mydataService;
	
	public MydataController(MydataService mydataService) {
		this.mydataService = mydataService;
	}


    @GetMapping(value="/test")
    // Swagger
    @ApiOperation("Get test")
    @ApiResponse(code = 200, message = "OK")
    public String test() {
        return "Hello from BusinessAssistant MyData!!!";
    }
    
    
    // String payload => Payload is the data normally sent by POST or PUT request.
    @PostMapping(value="/my-searches/{user_uuid}")
    public Search saveSearch(@PathVariable(value="user_uuid") String user_uuid, 
    						 @RequestBody String payload) {
    	
    	
    	return mydataService.saveSearch(payload, user_uuid);
    }

}
