package com.kbtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kbtest.service.PlaceService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService service;

    @GetMapping("/v1/place")
    public void searchPlace(@RequestParam("q") String keyword) {

    }

    @GetMapping("/v1/kakao/place")
    public Mono<String> testKakaoPlace(@RequestParam("q") String keyword) {
        return service.searchKakaoPlace(keyword);
    }
}