package com.itvillage.common.exception;

public class BackToPreviousMenuException extends RuntimeException {
    private final static String MESSAGE_BACK_TO_PREVIOUS_MENU = "⤴️ ️이전 메뉴로 돌아갑니다.";
    public BackToPreviousMenuException() {
        super(MESSAGE_BACK_TO_PREVIOUS_MENU);
    }
}
