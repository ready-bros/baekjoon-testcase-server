package com.baekjoon_testcase.baekjoon_testcase.dto;

import com.baekjoon_testcase.baekjoon_testcase.common.Language;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TestCodeRequest {
    private String code;
    private List<String> input;
    private List<String> answer;
    private int timeLimitSecond;
    private Language language;
}
