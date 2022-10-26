package com.kbtest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kbtest.client.KakaoClient;
import com.kbtest.client.CommonPlace;
import com.kbtest.client.CommonResponse;
import com.kbtest.client.NaverClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final KakaoClient kakaoClient;
    private final NaverClient naverClient;

    public Mono<CommonResponse> searchPlace(String keyword) {
        Mono<CommonResponse> kakaoResult = kakaoClient.searchPlace(keyword);
        Mono<CommonResponse> naverResult = naverClient.searchPlace(keyword);

        return Mono.zip(kakaoResult, naverResult).map(tuple -> {
            CommonResponse kakaoResponse = tuple.getT1();
            CommonResponse naverResponse = tuple.getT2();

            if (kakaoResponse == null || CollectionUtils.isEmpty(kakaoResponse.getPlaces())) {
                return naverResponse;
            } else if (naverResponse == null || CollectionUtils.isEmpty(naverResponse.getPlaces())) {
                return kakaoResponse;
            }

            Map<String, CommonPlace> placeMap = new HashMap<>();
            List<CommonPlace> kakaoPlaces = kakaoResponse.getPlaces();
            List<CommonPlace> naverPlaces = naverResponse.getPlaces();

            for (int i = 0; i < kakaoPlaces.size(); i++) {
                CommonPlace cur = kakaoPlaces.get(i);
                String replacedName = cur.getTitle().replaceAll("\\s", "");
                cur.getOrderMap().put("kakao", i);

                placeMap.put(replacedName, cur);
            }        
            
            return kakaoResponse;
        });
    }

    public Mono<CommonResponse> searchKakaoPlace(String keyword) {
        return kakaoClient.searchPlace(keyword);
    }

    public Mono<CommonResponse> searchNaverPlace(String keyword) {
        return naverClient.searchPlace(keyword);
    }
}
