package com.kbtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kbtest.dto.CommonResponse;
import com.kbtest.service.PlaceService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService service;

    @GetMapping("/v1/place")
    public Mono<CommonResponse> searchPlace(@RequestParam("q") String keyword) {
        return service.searchPlace(keyword);
    }

    @GetMapping("/v1/kakao/place")
    public Mono<CommonResponse> testKakaoPlace(@RequestParam("q") String keyword) {
        return service.searchKakaoPlace(keyword);
    }

    @GetMapping("/v1/naver/place")
    public Mono<CommonResponse> testNaverPlace(@RequestParam("q") String keyword) {
        return service.searchNaverPlace(keyword);
    }
}
