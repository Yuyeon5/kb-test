package com.kbtest.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.kbtest.dto.CommonResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class NaverClient {
    private final WebClient webClient;

    public NaverClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://openapi.naver.com")
                .defaultHeader("X-Naver-Client-Id", "Z9V0RmPIt55JXD4OFmHl")
                .defaultHeader("X-Naver-Client-Secret", "ZshQyVFbFi")
                .build();
    }

    public Mono<CommonResponse> searchPlace(String keyword) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/search/local.json").queryParam("query", keyword)
                        .queryParam("display", 5)
                        .build())
                .retrieve()
                .bodyToMono(CommonResponse.class)
                .doOnError(throwable -> log.error("naver search fail - {}", throwable.getMessage()))
                .onErrorReturn(new CommonResponse());
    }
}