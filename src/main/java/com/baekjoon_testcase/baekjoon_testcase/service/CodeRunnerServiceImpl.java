package com.baekjoon_testcase.baekjoon_testcase.service;

import com.baekjoon_testcase.baekjoon_testcase.common.Correct;
import com.baekjoon_testcase.baekjoon_testcase.common.Language;
import com.baekjoon_testcase.baekjoon_testcase.dto.CodeRunningResult;
import com.baekjoon_testcase.baekjoon_testcase.dto.TestCodeRequest;
import com.baekjoon_testcase.baekjoon_testcase.dto.TestCodeResponse;
import com.baekjoon_testcase.baekjoon_testcase.runner.Runner;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeRunnerServiceImpl implements CodeRunnerService{
    private final Map<Language, Runner> runnerMap;

    public TestCodeResponse runCode(TestCodeRequest testCodeRequest) {
        Runner runner = runnerMap.get(testCodeRequest.getLanguage());
        CodeRunningResult codeRunningResult = runner.run(1, testCodeRequest.getInput(), testCodeRequest.getCode());

        int runtimeLimit = runner.getRuntimeLimit(testCodeRequest.getTimeLimitSecond());

        if (codeRunningResult.getRuntime() >= runtimeLimit) {
            return new TestCodeResponse(1L, codeRunningResult.getRuntime(), Correct.TIME_LIMIT_EXCEEDED.getSuccess(), Correct.TIME_LIMIT_EXCEEDED.getReason());
        }

        if (!codeRunningResult.getOutput().equals(testCodeRequest.getAnswer())) {
            System.out.println(codeRunningResult.getOutput() + " :: " + testCodeRequest.getAnswer());
            return new TestCodeResponse(1L, codeRunningResult.getRuntime(), Correct.WRONG_ANSWER.getSuccess(), Correct.WRONG_ANSWER.getReason());
        }

        return new TestCodeResponse(1L, codeRunningResult.getRuntime(), Correct.CORRECT.getSuccess(), Correct.CORRECT.getReason());
    }
}
