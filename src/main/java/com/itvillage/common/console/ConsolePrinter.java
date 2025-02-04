package com.itvillage.common.console;

public class ConsolePrinter {
    public static void println(String outputContent) {
        System.out.println(outputContent);
    }

    public static void printf(String outputContent, Object... args) {
        String formattedString = String.format(outputContent, args);
        System.out.println(formattedString);
    }
}
