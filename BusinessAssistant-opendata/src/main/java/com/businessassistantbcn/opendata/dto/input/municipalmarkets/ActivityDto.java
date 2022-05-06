package com.businessassistantbcn.opendata.dto.input.municipalmarkets;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ActivityDto {
    @JsonProperty("id")
    public int getId() {
        return this.id; }
    public void setId(int id) {
        this.id = id; }
    private int id;

    @JsonProperty("name")
    public String getName() {
        return this.name; }
    public void setName(String name) {
        this.name = name; }
    private String name;
}