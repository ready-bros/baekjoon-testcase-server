package com.baekjoon_testcase.baekjoon_testcase.runner;

import com.baekjoon_testcase.baekjoon_testcase.dto.CodeRunningResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JavaRunner implements Runner {
    @Value("${file.name}")
    private String FILE_NAME;

    @Override
    public CodeRunningResult run(long id, String input, String extension) {
        try {
            compileCode(id, extension);
            ProcessBuilder processBuilder = new ProcessBuilder("java",
                    "-cp",
                    "code/" + id,
                    "-Xms1024m",
                    "-Xmx1920m",
                    "-Xss512m",
                    "-Dfile.encoding=UTF-8",
                    "-XX:+UseSerialGC",
                    "-DONLINE_JUDGE=1",
                    "-DBOJ=1",
                    this.FILE_NAME);

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

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

            long startTime = System.currentTimeMillis();

            // 입력 처리
            try (OutputStream os = process.getOutputStream()) {
                os.write((input + "\n").getBytes());
                os.flush();
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

    private void compileCode(long id, String extension) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("javac",
                    "--release",
                    "11",
                    "-J-Xms1024m",
                    "-J-Xmx1920m",
                    "-J-Xss512m",
                    "-encoding",
                    "UTF-8",
                    "code/" + id + "/" + this.FILE_NAME + extension);
            System.out.println(Files.readString(Path.of("code/" + id + "/" + this.FILE_NAME + extension)));

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // 에러 캡처
            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println("Error: " + errorLine);
            }

            System.out.println(process.waitFor() + "*");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("컴파일 예외", e);
        }
    }
}
