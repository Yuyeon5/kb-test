package com.kbtest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.stereotype.Service;

import com.kbtest.dto.Keyword;

@Service
public class KeywordService {

  private static final int NUMBER_KEYWORD = 10;

  private final ConcurrentHashMap<String, Integer> keywordMap = new ConcurrentHashMap<>();
  private final PriorityBlockingQueue<Keyword> keywordQueue = new PriorityBlockingQueue<>();

  private int threshold = 0;

  public void incKeyword(String keyword) {
    Integer oldValue = keywordMap.putIfAbsent(keyword, 1);
    Integer newValue = 1;

    if (oldValue != null) {
      newValue = keywordMap.computeIfPresent(keyword, (k, v) -> v + 1);
    }

    // Update top keyword map
    if (newValue > threshold) {
      topKeywordMap.put(keyword, newValue);
    }
  }

  public List<Keyword> getTopKeywords() {
    

  }
}
