package com.businessassistantbcn.gencat.dto.input;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
@JsonIgnoreProperties({"meta"})
public class CcaeDto {

    @JsonProperty("data")
    private List<Object> data;

    @JsonGetter("data")
    public List<Object> getData() {
        return data;
    }

    @JsonSetter("data")
    public void setData(List<Object> data) {
        this.data = data;
    }

   /* public Object getId(){
        return data.get(1);
    }

    public String getType(){
        return data.get(9);
    }

    public String getIdCcae(){
        return data.get(8);
    }

    public String getDescription(){
        return data.get(10);
    }*/
}
