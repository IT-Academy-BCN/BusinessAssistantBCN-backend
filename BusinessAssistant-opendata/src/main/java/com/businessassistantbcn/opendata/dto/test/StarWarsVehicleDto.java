package com.businessassistantbcn.opendata.dto.test;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
public class StarWarsVehicleDto {
	
    private String name;
    private String model;
    private String manufacturer;
    private String cost_in_credits;
    private float length;
    private int max_atmosphering_speed;
    private int crew;
    private int passengers;
    private String cargo_capacity;
    private String vehicle_class;
    private String consumables;
    private String[] pilots;
    private String[] films;
    private String created;
    private String edited;
    private String url;
    
}
