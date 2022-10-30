package com.kbtest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbtest.dto.Keyword;
import com.kbtest.service.KeywordService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService service;

    @GetMapping("/v1/keywords")
    public Mono<List<Keyword>> getTopKeywords() {
        return Mono.just(service.getTopKeywords());
    }    
}
