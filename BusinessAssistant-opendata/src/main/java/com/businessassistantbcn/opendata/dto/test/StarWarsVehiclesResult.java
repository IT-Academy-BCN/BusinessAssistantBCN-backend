package com.businessassistantbcn.opendata.dto.test;

public class StarWarsVehiclesResult {


    public int count;
    public String next;
    public String previous;
    public StarWarsVehicle[] results;

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

    public StarWarsVehicle[] getResults() {
        return results;
    }

    public void setResults(StarWarsVehicle[] results) {
        this.results = results;
    }
}
