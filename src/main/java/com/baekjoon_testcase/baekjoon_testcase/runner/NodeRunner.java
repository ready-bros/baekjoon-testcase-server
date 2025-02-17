package com.baekjoon_testcase.baekjoon_testcase.runner;

import com.baekjoon_testcase.baekjoon_testcase.dto.CodeRunningResult;
import com.baekjoon_testcase.baekjoon_testcase.stream.StreamManager;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NodeRunner implements Runner {
    @Value("${file.name}")
    private String FILE_NAME;

    @Override
    public CodeRunningResult run(long id, String input, String extension) {
        try {
            Process process = startProcess(id, extension);
            StreamManager  streamManager = new StreamManager(process);

            int totalRuntime = sendInput(streamManager, process, input);

            System.err.println(streamManager.getError());
            String output = streamManager.getOutput();

            return new CodeRunningResult(totalRuntime, output);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("임시 코드 실행 예외", e);
        }
    }

    @Override
    public int getRuntimeLimit(int runtime) {
        return runtime * 3 + 2;
    }

    private Process startProcess(long id, String extension) throws IOException{
        ProcessBuilder processBuilder = new ProcessBuilder("node",
                "--stack-size=65536",
                "code/" + id + "/" + this.FILE_NAME + extension);

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
