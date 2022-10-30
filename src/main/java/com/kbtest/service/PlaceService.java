package com.kbtest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kbtest.client.KakaoClient;
import com.kbtest.client.NaverClient;
import com.kbtest.dto.CommonPlace;
import com.kbtest.dto.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {
    private final KakaoClient kakaoClient;
    private final NaverClient naverClient;
    private final KeywordService keywordService;

    // 카카오 - 10, 네이버 - 01 비트를 사용합니다.
    // 더 높은 우선순위 검색포털이 추가된다면 100 을 추가하면 됩니다.
    private static final int KAKAO_BIT = 1 << 1;
    private static final int NAVER_BIT = 1;

    private int comparePlace(CommonPlace a, CommonPlace b) {
        int aOrderBit = a.getOrderBit();
        int bOrderBit = b.getOrderBit();

        if (aOrderBit != bOrderBit) {
            return bOrderBit - aOrderBit;
        } else {
            return a.getOrder() - b.getOrder();
        }
    }

    // 카카오 장소 검색 결과를 placeMap 에 추가
    private void insertKakaoPlaces(CommonResponse kakaoResponse, Map<String, CommonPlace> placeMap) {
        if (kakaoResponse != null && !CollectionUtils.isEmpty(kakaoResponse.getPlaces())) {
            List<CommonPlace> kakaoPlaces = kakaoResponse.getPlaces();

            for (int i = 0; i < kakaoPlaces.size(); i++) {
                CommonPlace place = kakaoPlaces.get(i);
                String replacedName = place.getTitle().replaceAll("\\s", "");

                place.setOrder(i);
                place.setOrderBit(KAKAO_BIT);

                placeMap.put(replacedName, place);
            }
        }
    }

    // 네이버 장소 검색 결과를 placeMap 에 추가
    private void insertNaverPlaces(CommonResponse naverResponse, Map<String, CommonPlace> placeMap) {
        if (naverResponse != null && !CollectionUtils.isEmpty(naverResponse.getPlaces())) {
            List<CommonPlace> naverPlaces = naverResponse.getPlaces();

            for (int i = 0; i < naverPlaces.size(); i++) {
                CommonPlace place = naverPlaces.get(i);
                // Naver result includes <b> and </b>
                String replacedName = place.getTitle().replaceAll("\\s|\\<[^>]*>", "");

                if (placeMap.containsKey(replacedName)) {
                    place = placeMap.get(replacedName);
                    place.setOrderBit(place.getOrderBit() | NAVER_BIT);
                } else {
                    place.setOrder(i);
                    place.setOrderBit(NAVER_BIT);

                    placeMap.put(replacedName, place);
                }
            }
        }
    }

    public Mono<CommonResponse> searchPlace(String keyword) {
        Mono<CommonResponse> kakaoResult = kakaoClient.searchPlace(keyword);
        Mono<CommonResponse> naverResult = naverClient.searchPlace(keyword);

        // 키워드 빈도 증가
        keywordService.incKeyword(keyword);

        // 카카오와 네이버 장소 검색을 비동기로 합쳐서 처리
        return Mono.zip(kakaoResult, naverResult).map(tuple -> {
            CommonResponse kakaoResponse = tuple.getT1();
            CommonResponse naverResponse = tuple.getT2();
            Map<String, CommonPlace> placeMap = new HashMap<>();
            CommonResponse ret = new CommonResponse();

            insertKakaoPlaces(kakaoResponse, placeMap);
            insertNaverPlaces(naverResponse, placeMap);

            log.info("merged place count - {}", placeMap.size());

            if (placeMap.isEmpty()) {
                log.warn("Merged result is empty");
                return ret; // empty response
            }

            List<CommonPlace> mergedPlaces = new ArrayList<>(placeMap.values());
            Collections.sort(mergedPlaces, this::comparePlace);

            ret.setPlaces(mergedPlaces);

            return ret;
        });
    }
}
