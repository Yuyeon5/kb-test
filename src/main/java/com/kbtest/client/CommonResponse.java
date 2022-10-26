package com.kbtest.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {
    @JsonProperty("places")
    @JsonAlias({ "documents", "items" })
    List<CommonPlace> places;
}
