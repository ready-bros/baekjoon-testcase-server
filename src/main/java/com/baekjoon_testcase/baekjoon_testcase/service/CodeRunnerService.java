package com.baekjoon_testcase.baekjoon_testcase.service;

import com.baekjoon_testcase.baekjoon_testcase.dto.TestCodeRequest;
import com.baekjoon_testcase.baekjoon_testcase.dto.TestCodeResponse;

public interface CodeRunnerService {
    TestCodeResponse runCode(TestCodeRequest testCodeRequest);
}
