package com.kbtest.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonPlace {
    @JsonProperty("title")
    @JsonAlias({ "place_name" })
    String title;

    @JsonProperty("roadAddress")
    @JsonAlias({ "road_address_name" })
    String roadAddress;

    // 10 - kakao, 01 - naver, 11 - kakao/naver
    @JsonIgnore
    int orderBit;

    @JsonIgnore
    int order;
}
