package com.kbtest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Keyword implements Comparable<Keyword> {
  @JsonProperty("keyword")
  String keyword;

  @JsonProperty("frequency")
  int frequency;

  @Override
  public int compareTo(Keyword other) {
    return other.frequency - this.frequency;
  }
}
