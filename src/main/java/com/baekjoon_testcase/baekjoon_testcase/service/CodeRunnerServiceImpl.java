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
    private final AnswerComparator answerComparator;

    @Override
    public TestCodeResponse runCode(TestCodeRequest testCodeRequest) {
        Runner runner = runnerMap.get(testCodeRequest.getLanguage());
        Language language = testCodeRequest.getLanguage();
        runnerFileManager.saveCode(1L, testCodeRequest.getCode(), language.getFileExtension());
        List<String> inputList = testCodeRequest.getInput();
        List<String> answerList = testCodeRequest.getAnswer();

        validateInputAndAnswer(inputList, answerList);

        return runAllTestcases(runner, language, inputList, answerList, testCodeRequest.getTimeLimitSecond());
    }

    private void validateInputAndAnswer(List<String> inputList, List<String> answerList) {
        if (inputList.isEmpty()) {
            throw new IllegalArgumentException("input이 비어있습니다.");
        }

        if (inputList.size() != answerList.size()) {
            throw new IllegalArgumentException("input과 answer의 개수가 다릅니다.");
        }
    }

    private TestCodeResponse runAllTestcases(Runner runner, Language language, List<String> inputList, List<String> answerList, int timeLimit) {
        int totalTestRuntime = 0;

        for (int runningIndex = 0; runningIndex < inputList.size(); runningIndex++) {
            CodeRunningResult codeRunningResult = runner.run(1L, inputList.get(runningIndex), language.getFileExtension());
            int runtimeLimit = runner.getRuntimeLimit(timeLimit);

            CompareResult compareResult = this.answerComparator.compare(codeRunningResult, runtimeLimit, answerList.get(runningIndex));
            totalTestRuntime += compareResult.getRuntime();

            if (!compareResult.isSuccess()) {
                return new TestCodeResponse(1L, totalTestRuntime / inputList.size(), false, compareResult.getReason());
            }
        }

        return new TestCodeResponse(1L, totalTestRuntime / inputList.size(), Correct.CORRECT.getSuccess(), Correct.CORRECT.getReason());
    }
}
