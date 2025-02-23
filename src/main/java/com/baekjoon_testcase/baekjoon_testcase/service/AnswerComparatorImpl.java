package com.baekjoon_testcase.baekjoon_testcase.service;

import com.baekjoon_testcase.baekjoon_testcase.common.Correct;
import com.baekjoon_testcase.baekjoon_testcase.dto.CodeRunningResult;
import com.baekjoon_testcase.baekjoon_testcase.dto.CompareResult;
import org.springframework.stereotype.Component;

@Component
public class AnswerComparatorImpl implements AnswerComparator {
    @Override
    public CompareResult compare(CodeRunningResult result, int runtimeLimit, String answer) {
        int runtime = result.getRuntime();
        String output = result.getOutput();

        CompareResult compareResult = new CompareResult(runtime);

        Correct correct = checkCorrect(runtime, runtimeLimit, output, answer);
        compareResult.setCorrect(correct);

        return compareResult;
    }

    private Correct checkCorrect(int runtime, int runtimeLimit, String output, String answer) {
        if (runtime >= runtimeLimit) {
            return Correct.TIME_LIMIT_EXCEEDED;
        }

        if (!output.equals(answer)) {
            return Correct.WRONG_ANSWER;
        }

        return Correct.CORRECT;
    }
}
