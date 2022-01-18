package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties({
        "id","value","integer_value","float_value","char_value","text_value","datetime_value","option_value",/*"email_value",*/
        /*"phone_value",*//*"url_value",*/"url_visible_value","url_visible_value","url_visible_value","contact_person_value",
        "responsible_value","icon_option_value","outstanding","description","attribute","category_name","attribute_name","attribute_type",
        "option_value_data","icon_option_value_data","responsible_title","responsible_jobtitle" })

public class WebMailPhoneDto {

    private String url_value;
    private String email_value;
    private String phone_value;

    @JsonGetter("web") //deserialize
    public String getUrl_value() {
        return url_value;
    }

    @JsonSetter("url_value") //serialize
    public void setUrl_value(String url_value) {
        this.url_value = url_value;
    }

    @JsonGetter("email")
    public String getEmail_value() {
        return email_value;
    }

    @JsonSetter("email_value")
    public void setEmail_value(String email_value) {
        this.email_value = email_value;
    }


    @JsonGetter("phone")
    public String getPhone_value() {
        return phone_value;
    }

    @JsonSetter("phone_value")
    public void setPhone_value(String phone_value) {
        this.phone_value = phone_value;
    }


    // Fefault properties
    /*
    private int id;
    private String value;
    private int integer_value;
    private float float_value;
    private char char_value;
    private String text_value;
    private String datetime_value;
    private String option_value;
    private String email_value;
    private String phone_value;
    private String url_value;
    private String url_visible_value;
    private String contact_person_value;
    private String responsible_value;
    private String icon_option_value;
    private boolean outstanding;
    private string description;
    private int attribute;
    private String category_name;
    private String attribute_name;
    private String attribute_type;
    private String option_value_data;
    private String icon_option_value_data;
    private String responsible_title;
    private String responsible_jobtitle;
    */


}
