package com.kbtest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.kbtest.dto.Keyword;

@Service
public class KeywordService {

  private static final int NUMBER_KEYWORD = 10;

  private final ConcurrentHashMap<String, Integer> keywordMap = new ConcurrentHashMap<>();
  private final Map<String, Integer> topKeywordMap = new HashMap<>();

  private int threshold = 0;

  public void incKeyword(String keyword) {
    Integer oldValue = keywordMap.putIfAbsent(keyword, 1);
    Integer newValue = 1;

    if (oldValue != null) {
      newValue = keywordMap.computeIfPresent(keyword, (k, v) -> v + 1);
    }

    // Update top keyword map
    incTopKeyword(keyword, newValue);
  }

  private synchronized void incTopKeyword(String keyword, Integer freq) {
    if (threshold < freq) {
      topKeywordMap.put(keyword, freq);

      if (topKeywordMap.size() > NUMBER_KEYWORD) {
        // Remove one item
        String toRemove = StringUtils.EMPTY;
        for (Map.Entry<String, Integer> entry : topKeywordMap.entrySet()) {
          if (entry.getValue() == threshold) {
            toRemove = entry.getKey();
            break;
          }
        }

        topKeywordMap.remove(toRemove);

        // Update threshold
        threshold = topKeywordMap.values().stream().mapToInt(v -> v).min().orElse(0);
      }
    }
  }

  public synchronized List<Keyword> getTopKeywords() {
    List<Keyword> ret = new ArrayList<>();

    for (Map.Entry<String, Integer> entry : topKeywordMap.entrySet()) {
      Keyword toAdd = new Keyword(entry.getKey(), entry.getValue());
      ret.add(toAdd);
    }

    Collections.sort(ret, (a, b) -> a.getFrequency() - b.getFrequency());

    return ret;
  }
}
