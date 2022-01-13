package com.businessassistantbcn.opendata.dto.largestablishments;

import com.fasterxml.jackson.annotation.*;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties({ "id","value","integer_value","float_value","char_value","text_value","datetime_value","option_value",
        "url_visible_value","contact_person_value","responsible_value","icon_option_value","outstanding","description",
        "attribute","category_name","attribute_name","attribute_type","option_value_data","icon_option_value_data",
        "responsible_title","responsible_jobtitle"
})

public class Value {

    // Factorisized properties
    @JsonProperty("email_value")
    private String email_value; // email
    @JsonProperty("phone_value")
    private Object phone_value; // telefono
    @JsonProperty("url_value")
    private String url_value;  //web

    // Default properties below
    /*
    private int id;
    private String value; // aparece telefono o email
    private Object integer_value;
    private Object float_value;
    private String char_value;
    private Object text_value;
    private Object datetime_value;
    private int option_value;
    private String email_value; // email
    private Object phone_value; // telefono
    private String url_value;  //web
    private String url_visible_value;
    private Object contact_person_value;
    private Object responsible_value;
    private Object icon_option_value;
    private boolean outstanding;
    private String description;
    private int attribute;
    private String category_name;
    private String attribute_name;
    private String attribute_type;
    private Object option_value_data;
    private Object icon_option_value_data;
    private String responsible_title;
    private String responsible_jobtitle;
    */

}
