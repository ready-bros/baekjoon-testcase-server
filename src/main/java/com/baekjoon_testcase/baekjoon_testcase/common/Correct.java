package com.baekjoon_testcase.baekjoon_testcase.common;

import lombok.Getter;

@Getter
public enum Correct {
    CORRECT("성공"),
    TIME_LIMIT_EXCEEDED("시간 초과"),
    WRONG_ANSWER("실패")
    ;

    private final Boolean success;
    private final String reason;

   Correct(String reason) {
        this.reason = reason;
        this.success = reason.equals("성공");
    }
}
