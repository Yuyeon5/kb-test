package com.kbtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class KeywordController {


    @GetMapping("/v1/keywords")
    public Mono<String> getTopKeywords() {
        return Mono.empty();        
    }    
}
