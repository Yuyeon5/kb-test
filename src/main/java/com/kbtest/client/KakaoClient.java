package com.kbtest.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class KakaoClient {
    private final WebClient webClient;

    @Value("client.kakao.url")
    private final String kakaoUrl;

    @Value("client.kakao.uri")
    private final String kakaoUri;

    @Value("client.kakao.key")
    private final String kakaoKey;

    public Mono<String> searchPlace(String keyword) {
        return webClient.mutate().baseUrl(kakaoUrl).defaultHeader("Authorization", kakaoKey).build().get()
                .uri(uriBuilder -> uriBuilder.path(kakaoUri).queryParam("query", keyword).build()).retrieve()
                .bodyToMono(String.class);
    }

}
