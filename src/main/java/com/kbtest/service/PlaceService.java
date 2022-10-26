package com.kbtest.service;

import org.springframework.stereotype.Service;

import com.kbtest.client.KakaoClient;
import com.kbtest.client.KakaoResponse;
import com.kbtest.client.NaverClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final KakaoClient kakaoClient;
    private final NaverClient naverClient;

    public Mono<KakaoResponse> searchKakaoPlace(String keyword) {
        return kakaoClient.searchPlace(keyword);
    }

    public Mono<String> searchNaverPlace(String keyword) {
        return naverClient.searchPlace(keyword);
    }
}
