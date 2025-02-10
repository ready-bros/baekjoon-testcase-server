package com.baekjoon_testcase.baekjoon_testcase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TestCodeResponse {
    long id;
    int runtime;
    boolean success;
    String reason;
}
