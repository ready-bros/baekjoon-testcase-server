package com.baekjoon_testcase.baekjoon_testcase.runner;

import com.baekjoon_testcase.baekjoon_testcase.dto.CodeRunningResult;

public interface Runner {
    CodeRunningResult run(long id, String input, String code);
    int getRuntimeLimit(int runtime);
}
