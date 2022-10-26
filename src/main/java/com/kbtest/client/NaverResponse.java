package com.kbtest.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class NaverResponse {
    @JsonProperty("items")
    List<Item> items;

    static class Item {
        @JsonProperty("title")
        String title;

        @JsonProperty("telephone")
        String telephone;

        @JsonProperty("roadAddress")
        String roadAddress;
    }
}
