package com.kbtest.client;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class CommonPlace {
    @JsonProperty("title")
    @JsonAlias({ "place_name" })
    String title;

    @JsonProperty("roadAddress")
    @JsonAlias({ "road_address_name" })
    String roadAddress;

    @JsonIgnore
    Map<String, Integer> orderMap = new HashMap<>();
}
