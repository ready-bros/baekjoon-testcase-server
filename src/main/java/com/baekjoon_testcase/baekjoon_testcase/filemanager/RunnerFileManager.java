package com.baekjoon_testcase.baekjoon_testcase.filemanager;

public interface RunnerFileManager {
    void saveCode(long id, String code, String extension);
    void saveOutput(long id, String output);
}
