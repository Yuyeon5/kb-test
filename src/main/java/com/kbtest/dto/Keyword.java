package com.kbtest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Keyword {
  @JsonProperty("word")
  String word;

  @JsonProperty("frequency")
  int frequency;
}
