package com.baekjoon_testcase.baekjoon_testcase.runner;

import com.baekjoon_testcase.baekjoon_testcase.dto.CodeRunningResult;

public interface Runner {
    CodeRunningResult run(long id, String input, String extension);
    int getRuntimeLimit(int runtime);
}
