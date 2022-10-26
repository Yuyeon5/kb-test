package com.kbtest.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class KakaoResponse {
    @JsonProperty("documents")
    List<Document> documents;

    static class Document {
        @JsonProperty("place_name")
        String placeName;        
    }
}
