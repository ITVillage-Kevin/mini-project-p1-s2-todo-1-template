package com.itvillage.common.exception;

public class AlreadyCompletedException extends RuntimeException {
    private final static String MESSAGE_ALREADY_COMPLETED = "# 이미 완료 처리된 할 일입니다.";

    public AlreadyCompletedException() {
        super(MESSAGE_ALREADY_COMPLETED);
    }
}
