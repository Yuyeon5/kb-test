package com.kbtest.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {
    // 네이버/카카오를 모두 지원하기 위한 포맷입니다.
    
    @JsonProperty("places")
    @JsonAlias({ "documents", "items" })
    List<CommonPlace> places = new ArrayList<>();
}
