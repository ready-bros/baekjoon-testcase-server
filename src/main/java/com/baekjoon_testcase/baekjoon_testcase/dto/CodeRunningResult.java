package com.baekjoon_testcase.baekjoon_testcase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CodeRunningResult {
    private int runtime;
    private String output;
}
