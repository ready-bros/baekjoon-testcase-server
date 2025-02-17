package com.baekjoon_testcase.baekjoon_testcase.runner;

import com.baekjoon_testcase.baekjoon_testcase.dto.CodeRunningResult;
import com.baekjoon_testcase.baekjoon_testcase.stream.StreamManager;
import java.io.IOException;
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
            return runCode(input, id);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("임시 코드 실행 예외", e);
        }
    }

    @Override
    public int getRuntimeLimit(int runtime) {
        return runtime * 3 + 2;
    }

    private void compileCode(long id, String extension) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("javac",
                "--release",
                "11",
                "-J-Xms1024m",
                "-J-Xmx1920m",
                "-J-Xss512m",
                "-encoding",
                "UTF-8",
                "code/" + id + "/" + this.FILE_NAME + extension);

        Process process = startProcess(processBuilder);

        StreamManager streamManager = new StreamManager(process);
        sendInput(streamManager, process, "");
    }

    private CodeRunningResult runCode(String input, long id) throws IOException, InterruptedException {
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
        Process process = startProcess(processBuilder);
        StreamManager streamManager = new StreamManager(process);

        int totalRuntime = sendInput(streamManager, process, input);

        System.err.println(streamManager.getError());
        String output = streamManager.getOutput();

        return new CodeRunningResult(totalRuntime, output);
    }

    private Process startProcess(ProcessBuilder processBuilder) throws IOException{
        processBuilder.redirectErrorStream(true);
        return processBuilder.start();
    }

    private int sendInput(StreamManager streamManager, Process process, String input) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();

        streamManager.sendInput(input); // 파라미터 입력
        process.waitFor(); // 코드 종료 대기

        return (int) (System.currentTimeMillis() - startTime);
    }
}
