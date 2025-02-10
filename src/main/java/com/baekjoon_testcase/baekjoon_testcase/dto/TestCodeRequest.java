package com.baekjoon_testcase.baekjoon_testcase.dto;

import com.baekjoon_testcase.baekjoon_testcase.common.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TestCodeRequest {
    private String code;
    private String input;
    private String answer;
    private int timeLimitSecond;
    private Language language;
}
