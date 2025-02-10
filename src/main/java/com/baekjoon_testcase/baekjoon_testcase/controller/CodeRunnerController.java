package com.baekjoon_testcase.baekjoon_testcase.controller;

import com.baekjoon_testcase.baekjoon_testcase.dto.TestCodeRequest;
import com.baekjoon_testcase.baekjoon_testcase.dto.TestCodeResponse;
import com.baekjoon_testcase.baekjoon_testcase.service.CodeRunnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("run")
@RequiredArgsConstructor
public class CodeRunnerController {
    private final CodeRunnerService codeRunnerService;

    @PostMapping()
    public TestCodeResponse runCode(@RequestBody TestCodeRequest testCodeRequest) {
        return codeRunnerService.runCode(testCodeRequest);
    }
}
