package com.baekjoon_testcase.baekjoon_testcase.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class StreamManager {
    private final InputStreamReader inputStreamReader;
    private final InputStreamReader errorStreamReader;
    private final OutputStream outputStream;

    public StreamManager(Process process) {
        this.inputStreamReader = new InputStreamReader(process.getInputStream());
        this.errorStreamReader = new InputStreamReader(process.getErrorStream());
        this.outputStream = process.getOutputStream();
    }

    public String getError() throws IOException{
        return getStreamResult(this.errorStreamReader);
    }

    public String getOutput() throws IOException {
        return getStreamResult(this.inputStreamReader);
    }

    public void sendInput(String input) throws IOException {
        this.outputStream.write((input + "\n").getBytes());
        this.outputStream.flush();
        this.outputStream.close();
    }

    private String getStreamResult(InputStreamReader inputStreamReader) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line)
                        .append("\n");
            }
        }

        return result.toString();
    }
}
