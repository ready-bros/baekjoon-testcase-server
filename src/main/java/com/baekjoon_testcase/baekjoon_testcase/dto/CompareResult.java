package com.baekjoon_testcase.baekjoon_testcase.dto;

import com.baekjoon_testcase.baekjoon_testcase.common.Correct;
import lombok.Getter;

public class CompareResult {
    @Getter
    int runtime;
    boolean success;
    @Getter
    String reason;

    public CompareResult(int runtime) {
        this.runtime = runtime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setCorrect(Correct correct) {
        if (this.reason != null) {
            return;
        }

        this.success = correct.getSuccess();
        this.reason = correct.getReason();
    }
}
