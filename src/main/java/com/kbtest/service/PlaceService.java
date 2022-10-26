package com.kbtest.service;

import org.springframework.stereotype.Service;

import com.kbtest.client.KakaoClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final KakaoClient kakaoClient;

    public Mono<String> searchKakaoPlace(String keyword) {
        return kakaoClient.searchPlace(keyword);
    }
}
