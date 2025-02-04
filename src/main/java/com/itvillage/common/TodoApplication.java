package com.itvillage.common;

import com.itvillage.common.console.ConsoleInputHandler;
import com.itvillage.common.console.ConsoleMenu;
import com.itvillage.common.console.ConsolePrinter;
import com.itvillage.common.handler.TodoConsoleHandler;
import com.itvillage.common.repository.InMemoryTodoRepository;
import com.itvillage.common.service.TodoService;

public class TodoApplication {
    private final static TodoService<Integer> todoService = new TodoService<>(new InMemoryTodoRepository());
    private final static TodoConsoleHandler<Integer> todoConsoleHandler = new TodoConsoleHandler<>(todoService);

    public static void run() {
        while (true) {
            try {
                // (1) 할 일 관리 메뉴 조회
                String todoMenu = ConsoleMenu.getMenuDescription();

                // (2) 할 일 관리 메뉴 출력
                ConsolePrinter.println(todoMenu);

                // (3) 할 일 관리 메뉴 선택
                selectMenu();
            } catch (Exception ex) {
                ConsolePrinter.println(ex.getMessage());
            }

        }

    }

    private static void selectMenu() {
        String selectedMenuNumber = ConsoleInputHandler.inputValue();

        // TodoConsoleHandler 클래스의 메서드를 호출하는 로직이 추가되어야 합니다.
        todoConsoleHandler.handle(selectedMenuNumber);
    }
}
