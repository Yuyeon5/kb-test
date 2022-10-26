package com.kbtest.service;

import org.springframework.stereotype.Service;

import com.kbtest.client.KakaoClient;
import com.kbtest.client.CommonResponse;
import com.kbtest.client.NaverClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final KakaoClient kakaoClient;
    private final NaverClient naverClient;

    public Mono<CommonResponse> searchKakaoPlace(String keyword) {
        return kakaoClient.searchPlace(keyword);
    }

    public Mono<CommonResponse> searchNaverPlace(String keyword) {
        return naverClient.searchPlace(keyword);
    }
}
