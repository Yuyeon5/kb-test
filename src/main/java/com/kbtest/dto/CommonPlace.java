package com.kbtest.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonPlace {
    // 네이버/카카오를 모두 지원하기 위한 포맷입니다.

    @JsonProperty("title")
    @JsonAlias({ "place_name" })
    String title;

    @JsonProperty("roadAddress")
    @JsonAlias({ "road_address_name" })
    String roadAddress;

    // 내부적으로 정렬시에 사용할 필드입니다.
    // 10 - kakao, 01 - naver, 11 - kakao/naver
    @JsonIgnore
    int orderBit;

    @JsonIgnore
    int order;
}
