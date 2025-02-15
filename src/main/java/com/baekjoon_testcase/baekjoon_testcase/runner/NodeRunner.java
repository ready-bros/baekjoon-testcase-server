package com.baekjoon_testcase.baekjoon_testcase.runner;

import com.baekjoon_testcase.baekjoon_testcase.dto.CodeRunningResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import org.springframework.stereotype.Component;

@Component
public class NodeRunner implements Runner {
    @Override
    public CodeRunningResult run(long id, String input, String extension) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("node",
                    "--stack-size=65536",
                    "code/" + id + extension);

            // 출력 및 에러 파이프라인 통합
            processBuilder.redirectErrorStream(true);
            long startTime = System.currentTimeMillis();
            Process process = processBuilder.start();

            // 입력 처리
            try (OutputStream os = process.getOutputStream()) {
                os.write((input + "\n").getBytes());
                os.flush();
            }

            // 에러 캡처
            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println("Error: " + errorLine);
            }

            // 출력 캡처
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            process.waitFor();
            int totalRuntime = (int) (System.currentTimeMillis() - startTime);

            return new CodeRunningResult(totalRuntime, output.toString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("임시 코드 실행 예외", e);
        }
    }

    @Override
    public int getRuntimeLimit(int runtime) {
        return runtime * 3 + 2;
    }
}
