package com.businessassistantbcn.opendata.dto.commercialgalleries;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component("CommercialGalleriesValueDto")
@JsonIgnoreProperties({
        "id","value","integer_value","float_value","char_value","text_value","datetime_value","option_value",/*"email_value",*/
        /*"phone_value",*//*"url_value",*/"url_visible_value","url_visible_value","url_visible_value","contact_person_value",
        "responsible_value","icon_option_value","outstanding","description","attribute","category_name","attribute_name","attribute_type",
        "option_value_data","icon_option_value_data","responsible_title","responsible_jobtitle" })
public class ContactDto {

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

}
