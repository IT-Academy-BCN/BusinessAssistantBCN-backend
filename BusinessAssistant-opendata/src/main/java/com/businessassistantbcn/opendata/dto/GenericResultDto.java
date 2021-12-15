package com.businessassistantbcn.opendata.dto;

import org.springframework.stereotype.Component;

@Component
public class GenericResultDto<T> {

    public int count;
    public T[] results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T[] getResults() {
        return results;
    }

    public void setResults(T[] results) {
        this.results = results;
    }
}
