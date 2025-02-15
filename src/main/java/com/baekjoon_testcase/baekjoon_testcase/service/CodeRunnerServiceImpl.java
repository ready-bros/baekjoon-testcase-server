package com.baekjoon_testcase.baekjoon_testcase.service;

import com.baekjoon_testcase.baekjoon_testcase.common.Correct;
import com.baekjoon_testcase.baekjoon_testcase.common.Language;
import com.baekjoon_testcase.baekjoon_testcase.dto.CodeRunningResult;
import com.baekjoon_testcase.baekjoon_testcase.dto.TestCodeRequest;
import com.baekjoon_testcase.baekjoon_testcase.dto.TestCodeResponse;
import com.baekjoon_testcase.baekjoon_testcase.filemanager.RunnerFileManager;
import com.baekjoon_testcase.baekjoon_testcase.runner.Runner;
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
        runnerFileManager.saveCode(1L, testCodeRequest.getCode(), testCodeRequest.getLanguage().getFileExtension());

        CodeRunningResult codeRunningResult = runner.run(1L, testCodeRequest.getInput(), testCodeRequest.getLanguage().getFileExtension());
        int runtime = codeRunningResult.getRuntime();
        int runtimeLimit = runner.getRuntimeLimit(testCodeRequest.getTimeLimitSecond());
        String output = codeRunningResult.getOutput();
        runnerFileManager.saveOutput(1L, output);

        return compareAnswer(runtime, runtimeLimit, output, testCodeRequest);
    }

    private  TestCodeResponse compareAnswer(int runtime, int runtimeLimit, String output, TestCodeRequest testCodeRequest) {
        if (runtime >= runtimeLimit) {
            return new TestCodeResponse(1L, runtime, Correct.TIME_LIMIT_EXCEEDED.getSuccess(), Correct.TIME_LIMIT_EXCEEDED.getReason());
        }

        if (!output.equals(testCodeRequest.getAnswer())) {
            return new TestCodeResponse(1L, runtime, Correct.WRONG_ANSWER.getSuccess(), Correct.WRONG_ANSWER.getReason());
        }

        return new TestCodeResponse(1L, runtime, Correct.CORRECT.getSuccess(), Correct.CORRECT.getReason());
    }
}
