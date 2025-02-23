package com.baekjoon_testcase.baekjoon_testcase.service;

import com.baekjoon_testcase.baekjoon_testcase.common.Correct;
import com.baekjoon_testcase.baekjoon_testcase.dto.CodeRunningResult;
import com.baekjoon_testcase.baekjoon_testcase.dto.CompareResult;
import org.springframework.stereotype.Component;

@Component
public class AnswerComparator {
    public CompareResult compare(CodeRunningResult result, int runtimeLimit, String answer) {
        int runtime = result.getRuntime();
        String output = result.getOutput();

        if (runtime >= runtimeLimit) {
            return new CompareResult(runtime, Correct.TIME_LIMIT_EXCEEDED.getSuccess(), Correct.TIME_LIMIT_EXCEEDED.getReason());
        }

        if (!output.equals(answer)) {
            return new CompareResult(runtime, Correct.WRONG_ANSWER.getSuccess(), Correct.WRONG_ANSWER.getReason());
        }

        return new CompareResult(runtime, Correct.CORRECT.getSuccess(), Correct.CORRECT.getReason());
    }
}
