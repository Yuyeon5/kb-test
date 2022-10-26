package com.kbtest.service;

import java.util.ArrayList;
import java.util.Collections;
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
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {
    private final KakaoClient kakaoClient;
    private final NaverClient naverClient;

    private static final String KAKAO = "kakao";
    private static final String NAVER = "naver";

    private int comparePlace(CommonPlace a, CommonPlace b) {
        Map<String, Integer> aOrderMap = a.getOrderMap();
        Map<String, Integer> bOrderMap = b.getOrderMap();

        int numResultA = aOrderMap.size();
        int numResultB = bOrderMap.size();
        int diff = numResultB - numResultA;

        if (diff != 0) {
            return diff;
        } else if (aOrderMap.containsKey(KAKAO) && bOrderMap.containsKey(KAKAO)) {
            return aOrderMap.get(KAKAO) - bOrderMap.get(KAKAO);
        } else if (aOrderMap.containsKey(NAVER) && bOrderMap.containsKey(NAVER)) {
            return aOrderMap.get(NAVER) - bOrderMap.get(NAVER);
        }

        if (aOrderMap.containsKey(KAKAO)) {
            return -1;
        } else {
            return 1;
        }
    }

    public Mono<CommonResponse> searchPlace(String keyword) {
        Mono<CommonResponse> kakaoResult = kakaoClient.searchPlace(keyword);
        Mono<CommonResponse> naverResult = naverClient.searchPlace(keyword);

        return Mono.zip(kakaoResult, naverResult).map(tuple -> {
            CommonResponse kakaoResponse = tuple.getT1();
            CommonResponse naverResponse = tuple.getT2();

            if (kakaoResponse == null || CollectionUtils.isEmpty(kakaoResponse.getPlaces())) {
                log.warn("kakao fails");
                return naverResponse;
            } else if (naverResponse == null || CollectionUtils.isEmpty(naverResponse.getPlaces())) {
                log.warn("naver fails");
                return kakaoResponse;
            }

            Map<String, CommonPlace> placeMap = new HashMap<>();
            List<CommonPlace> kakaoPlaces = kakaoResponse.getPlaces();
            List<CommonPlace> naverPlaces = naverResponse.getPlaces();

            for (int i = 0; i < kakaoPlaces.size(); i++) {
                CommonPlace place = kakaoPlaces.get(i);
                String replacedName = place.getTitle().replaceAll("\\s", "");
                place.getOrderMap().put(KAKAO, i);

                placeMap.put(replacedName, place);
            }

            for (int i = 0; i < naverPlaces.size(); i++) {
                CommonPlace place = naverPlaces.get(i);
                // Naver result includes <b> and </b>
                String replacedName = place.getTitle().replaceAll("\\s|\\<[^>]*>", "");

                if (placeMap.containsKey(replacedName)) {
                    place = placeMap.get(replacedName);
                } else {
                    placeMap.put(replacedName, place);
                }

                place.getOrderMap().put(NAVER, i);
            }

            log.info("merged place count - {}", placeMap.size());

            List<CommonPlace> mergedPlaces = new ArrayList<>(placeMap.values());
            Collections.sort(mergedPlaces, this::comparePlace);

            CommonResponse ret = new CommonResponse();
            ret.setPlaces(mergedPlaces);

            return ret;
        });
    }

    public Mono<CommonResponse> searchKakaoPlace(String keyword) {
        return kakaoClient.searchPlace(keyword);
    }

    public Mono<CommonResponse> searchNaverPlace(String keyword) {
        return naverClient.searchPlace(keyword);
    }
}
