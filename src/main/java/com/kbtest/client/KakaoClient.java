package com.kbtest.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class KakaoClient {
    private final WebClient webClient;

    @Value("client.kakao.url")
    private String kakaoUrl;

    @Value("client.kakao.uri")
    private String kakaoUri;

    @Value("client.kakao.key")
    private String kakaoKey;

    public KakaoClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("dapi.kakao.com")
                .defaultHeader("Authorization", "KakaoAK 283a7a18acec1b73460173469500bea3").build();
    }

    public Mono<String> searchPlace(String keyword) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v2/local/search/address.json").queryParam("query", keyword).build()).retrieve()
                .bodyToMono(String.class);
    }

}
