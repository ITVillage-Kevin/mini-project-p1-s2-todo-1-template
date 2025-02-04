package com.itvillage.common.console;

import com.itvillage.common.exception.BackToPreviousMenuException;

import java.util.Scanner;

/**
 * 입력 값을 핸들링하는 클래스
 */
public class ConsoleInputHandler {
    private final static Scanner scanner = new Scanner(System.in);

    public static String inputValue() {
        String value = scanner.nextLine();
        preCheck(value);
        return value;
    }

    public static void clearInputValue() {
        scanner.nextLine();
    }

    private static void preCheck(String value) {
        if (shouldBackToPrevious(value)) {
            throw new BackToPreviousMenuException();
        }
    }

    private static boolean shouldBackToPrevious(String value) {
        return value.equals(":q");
    }
}
