package com.kbtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceController {
    @GetMapping("/v1/place")
    public void searchPlace(@RequestParam("q") String keyword) {

    }
}
