package com.businessassistantbcn.opendata.dto.test;

import org.springframework.stereotype.Component;

@Component
public class StarWarsVehiclesResultDto {


    public int count;
    public String next;
    public String previous;
    public StarWarsVehicleDto[] results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public StarWarsVehicleDto[] getResults() {
        return results;
    }

    public void setResults(StarWarsVehicleDto[] results) {
        this.results = results;
    }
}
