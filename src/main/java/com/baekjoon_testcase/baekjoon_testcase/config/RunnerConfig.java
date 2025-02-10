package com.baekjoon_testcase.baekjoon_testcase.config;

import com.baekjoon_testcase.baekjoon_testcase.common.Language;
import com.baekjoon_testcase.baekjoon_testcase.runner.NodeRunner;
import com.baekjoon_testcase.baekjoon_testcase.runner.Runner;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RunnerConfig {
    private final NodeRunner nodeRunner;

    @Bean
    public Map<Language, Runner> runnerMap() {
        Map<Language, Runner> runnerMap = new HashMap<>();
        runnerMap.put(Language.NODE, nodeRunner);
        return runnerMap;
    }
}
