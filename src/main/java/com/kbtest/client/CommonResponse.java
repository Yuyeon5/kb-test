package com.kbtest.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class CommonResponse {
    @JsonProperty("documents")
    @JsonAlias("items")
    List<Place> places;

    static class Place {
        @JsonProperty("place_name")
        @JsonAlias("title")
        String name;        
    }
}
