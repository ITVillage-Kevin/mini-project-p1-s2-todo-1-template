package com.itvillage.common.console;

public class ConsoleMenu {
    public static String getMenuDescription() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("-".repeat(60));
        stringBuilder.append("\n아래 메뉴에서 메뉴 번호를 입력해 주세요. ex) 1\n");
        stringBuilder.append("(1) 할일 등록\n");
        stringBuilder.append("(2) 할일 완료 처리\n");
        stringBuilder.append("(0) 애플리케이션 종료\n");

        return stringBuilder.toString();
    }
}
