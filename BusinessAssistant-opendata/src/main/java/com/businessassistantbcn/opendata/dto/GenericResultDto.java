package com.businessassistantbcn.opendata.dto;

import org.springframework.stereotype.Component;
import java.util.stream.Stream;

@Component
public class GenericResultDto<T> {

    public int count;
    public int offset;
    public int limit;
    public T[] results;




    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setResults(T[] results) {
        this.results = results;
    }

    public void setResults(T[] results, int offset, int limit) {
        this.results= (T[]) Stream.of(results).skip(offset).limit(limit).toArray();
        this.offset=offset;
        if (limit> results.length) {
            this.limit = results.length;
        }else this.limit=limit;
    }


    public T[] getResults() {
        return results;
    }


    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }



}
