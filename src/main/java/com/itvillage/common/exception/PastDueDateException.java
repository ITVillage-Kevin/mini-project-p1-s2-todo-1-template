package com.itvillage.common.exception;

public class PastDueDateException extends RuntimeException {
    private final static String MESSAGE_PAST_DUE_DATE = "# 할 일 수행 날짜는 현재 날짜보다 크거나 같아야 합니다.";

    public PastDueDateException() {
        super(MESSAGE_PAST_DUE_DATE);
    }
}
