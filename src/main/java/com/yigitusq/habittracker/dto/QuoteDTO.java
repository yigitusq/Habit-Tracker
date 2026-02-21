package com.yigitusq.habittracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteDTO {

    @JsonProperty("q")
    private String text;

    @JsonProperty("a")
    private String author;
}