package com.baekjoon_testcase.baekjoon_testcase.common;

import lombok.Getter;

@Getter
public enum Language {
    NODE("node", ".js")
    ;

    private String name;
    private String fileExtension;

    Language(String name, String fileExtension) {
        this.name = name;
        this.fileExtension = fileExtension;
    }
}
