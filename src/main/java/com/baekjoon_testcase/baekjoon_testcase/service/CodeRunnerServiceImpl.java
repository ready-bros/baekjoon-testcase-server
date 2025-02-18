package com.baekjoon_testcase.baekjoon_testcase.service;

import com.baekjoon_testcase.baekjoon_testcase.common.Correct;
import com.baekjoon_testcase.baekjoon_testcase.common.Language;
import com.baekjoon_testcase.baekjoon_testcase.dto.CodeRunningResult;
import com.baekjoon_testcase.baekjoon_testcase.dto.CompareResult;
import com.baekjoon_testcase.baekjoon_testcase.dto.TestCodeRequest;
import com.baekjoon_testcase.baekjoon_testcase.dto.TestCodeResponse;
import com.baekjoon_testcase.baekjoon_testcase.filemanager.RunnerFileManager;
import com.baekjoon_testcase.baekjoon_testcase.runner.Runner;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeRunnerServiceImpl implements CodeRunnerService{
    private final Map<Language, Runner> runnerMap;
    private final RunnerFileManager runnerFileManager;

    public TestCodeResponse runCode(TestCodeRequest testCodeRequest) {
        Runner runner = runnerMap.get(testCodeRequest.getLanguage());
        Language language = testCodeRequest.getLanguage();
        runnerFileManager.saveCode(1L, testCodeRequest.getCode(), language.getFileExtension());
        List<String> inputList = testCodeRequest.getInput();
        List<String> answerList = testCodeRequest.getAnswer();

        if (inputList.size() != answerList.size()) {
            throw new IllegalArgumentException("input과 answer의 개수가 다릅니다.");
        }

        return RunEachTestCase(runner, language, inputList, answerList, testCodeRequest.getTimeLimitSecond());
    }

    private CompareResult compareAnswer(int runtime, int runtimeLimit, String output, String answer) {
        if (runtime >= runtimeLimit) {
            return new CompareResult(runtime, Correct.TIME_LIMIT_EXCEEDED.getSuccess(), Correct.TIME_LIMIT_EXCEEDED.getReason());
        }

        if (!output.equals(answer)) {
            return new CompareResult(runtime, Correct.WRONG_ANSWER.getSuccess(), Correct.WRONG_ANSWER.getReason());
        }

        return new CompareResult(runtime, Correct.CORRECT.getSuccess(), Correct.CORRECT.getReason());
    }

    private TestCodeResponse RunEachTestCase(Runner runner, Language language, List<String> inputList, List<String> answerList, int timeLimit) {
        int totalTestRuntime = 0;

        for (int runningIndex = 0; runningIndex < inputList.size(); runningIndex++) {
            CodeRunningResult codeRunningResult = runner.run(1L, inputList.get(runningIndex), language.getFileExtension());
            int runtime = codeRunningResult.getRuntime();
            int runtimeLimit = runner.getRuntimeLimit(timeLimit);
            String output = codeRunningResult.getOutput();

            CompareResult compareResult = compareAnswer(runtime, runtimeLimit, output, answerList.get(runningIndex));
            totalTestRuntime += compareResult.getRuntime();

            if (!compareResult.isSuccess()) {
                return new TestCodeResponse(1L, totalTestRuntime / inputList.size(), false, compareResult.getReason());
            }
        }

        int runtimeAverage = totalTestRuntime / inputList.size();

        return new TestCodeResponse(1L, runtimeAverage, Correct.CORRECT.getSuccess(), Correct.CORRECT.getReason());
    }
}
