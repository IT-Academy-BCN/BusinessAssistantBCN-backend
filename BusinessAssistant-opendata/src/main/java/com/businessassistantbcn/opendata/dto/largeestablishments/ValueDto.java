package com.businessassistantbcn.opendata.dto.largeestablishments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@JsonIgnoreProperties({"option_value_data","responsible_value","contact_person_value", "icon_option_value","icon_option_value_data",
        "responsible_title","responsible_jobtitle"
})
public class ValueDto {
	
    private int id;
    private String value;
    private int integer_value;
    private float float_value;
    private String char_value;
    private String text_value;
    private String datetime_value;
    private String option_value;
    private String email_value;
    private String phone_value;
    private String url_value;
    private String url_visible_value;
//    private String contact_person_value;
//    private String responsible_value;
//    private String icon_option_value;
    private boolean outstanding;
    public String description;
    private int attribute;
    private String category_name;
    private String attribute_name;
    private String attribute_type;
//    private String option_value_data;
//    private String icon_option_value_data;
    private String responsible_title;
    private String responsible_jobtitle;
    
}
