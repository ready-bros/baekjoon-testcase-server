package com.baekjoon_testcase.baekjoon_testcase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CompareResult {
    @Getter
    int runtime;
    boolean success;
    @Getter
    String reason;

    public boolean isSuccess() {
        return success;
    }
}
