package com.businessassistantbcn.usermanagement.dto.output;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class GenericResultDto<T>{
    private int offset;
    private int limit;
    private int count;

    private T[] results;

    private GenericResultDto() {
        //private no args constructor, used only for Integration tests
    }

    public GenericResultDto(T... results) {
        offset = 0; //default value when no pagintaion
        limit = -1; //default value when no pagintaion
        count = results.length;
        this.results = results;
    }
}