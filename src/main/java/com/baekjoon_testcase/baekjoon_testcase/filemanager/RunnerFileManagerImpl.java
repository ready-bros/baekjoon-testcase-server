package com.baekjoon_testcase.baekjoon_testcase.filemanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RunnerFileManagerImpl implements RunnerFileManager {
    @Value("${folder.name.code}")
    private String CODE_FOLDER_NAME;
    @Value("${folder.name.output}")
    private String OUTPUT_FOLDER_NAME;
    @Value("${file.name}")
    private String FILE_NAME;

    @Override
    public void saveCode(long id, String code, String extension) {
        try {
            validateCodeDirectory();

            String codeDirectory = this.CODE_FOLDER_NAME + "/" + id;

            Files.createDirectory(Path.of(codeDirectory));
            Files.writeString(Path.of(codeDirectory, this.FILE_NAME + extension), code);
        } catch (IOException e) {
            throw new RuntimeException("임시 코드 저장 예외", e);
        }
    }

    @Override
    public void saveOutput(long id, String output) {
        try {
            if (!Files.exists(Path.of(this.OUTPUT_FOLDER_NAME))) {
                Files.createDirectory(Path.of(this.OUTPUT_FOLDER_NAME));
            }
            Files.writeString(Path.of(this.OUTPUT_FOLDER_NAME + "/" + id + ".txt"), output);
        } catch (IOException e) {
            throw new RuntimeException("출력 결과 저장 예외", e);
        }
    }

    private void validateCodeDirectory() throws IOException {
        Path codeDirectoryPath = Path.of(this.CODE_FOLDER_NAME);

        if (!Files.exists(codeDirectoryPath)) {
            Files.createDirectory(codeDirectoryPath);
        }
    }
}
