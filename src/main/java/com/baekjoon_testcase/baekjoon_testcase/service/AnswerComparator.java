package com.baekjoon_testcase.baekjoon_testcase.service;

import com.baekjoon_testcase.baekjoon_testcase.dto.CodeRunningResult;
import com.baekjoon_testcase.baekjoon_testcase.dto.CompareResult;

public interface AnswerComparator {
    CompareResult compare(CodeRunningResult result, int runtimeLimit, String answer);
}
