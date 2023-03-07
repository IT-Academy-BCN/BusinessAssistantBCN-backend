package com.businessassistantbcn.gencat.dto;

import com.businessassistantbcn.gencat.dto.output.RaiscResponseDto;
import com.businessassistantbcn.gencat.dto.output.ScopeDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter @Setter
public class GenericResultDto<T> {

    private int offset;
    private int limit;
    private int count;

    private T[] results;

    private List<ScopeDto> resultsList;

    public GenericResultDto() {
        //Empty constructor
    }

    public GenericResultDto(int offset, int limit, int count, List<ScopeDto> results) {
        this.offset = offset;
        this.limit = limit;
        this.count = count;
        this.resultsList = results;
    }


    public void setInfo(int offset, int limit, int count, T[] results) {
        this.offset = offset;
        this.limit = limit;
        this.count = count;
        this.results = results;
    }

    public <T> void setInfo(List<T> singletonList) {

    }
}
