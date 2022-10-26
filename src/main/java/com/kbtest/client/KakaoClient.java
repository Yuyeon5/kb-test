package com.kbtest.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class KakaoClient {
    private final WebClient webClient;

    public KakaoClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://dapi.kakao.com")
                .defaultHeader("Authorization", "KakaoAK 283a7a18acec1b73460173469500bea3").build();
    }

    public Mono<KakaoResponse> searchPlace(String keyword) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v2/local/search/keyword.json").queryParam("query", keyword)
                        .queryParam("page", 1).queryParam("size", 5)
                        .build())
                .retrieve()
                .bodyToMono(KakaoResponse.class);
    }
}