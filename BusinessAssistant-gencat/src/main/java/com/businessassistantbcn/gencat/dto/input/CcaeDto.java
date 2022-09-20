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


    private List<List<String>> data;

    @JsonGetter("data")
    public List<List<String>> getData() {
        return data;
    }

    @JsonSetter("data")
    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
