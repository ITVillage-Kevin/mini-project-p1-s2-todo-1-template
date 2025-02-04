package com.itvillage.common.handler;

import com.itvillage.common.console.ConsoleInputHandler;
import com.itvillage.common.entity.Todo;
import com.itvillage.common.exception.BackToPreviousMenuException;
import com.itvillage.common.service.TodoService;
import com.itvillage.common.validator.InputValidator;

import java.time.LocalDate;
import java.util.List;

import static com.itvillage.common.console.ConsolePrinter.printf;
import static com.itvillage.common.console.ConsolePrinter.println;

public class TodoConsoleHandler<ID> {
    private final TodoService<ID> todoService;

    public TodoConsoleHandler(TodoService<ID> todoService) {
        this.todoService = todoService;
    }

    public void handle(String selectedMenuNumber) {
        switch (selectedMenuNumber) {
            case "1" -> registerTodo();
            case "2" -> completeTodo();
            case "0" -> exitApplication();
            default -> throw new IllegalArgumentException("유효한 메뉴 번호가 아닙니다: " + selectedMenuNumber);
        }
    }

    // 할 일 등록 처리
    private void registerTodo() {
        boolean registrationCompleted = false;

        while (!registrationCompleted) {
            try {
                // 할 일 정보 입력
                String todo = inputTodo();
                String dueDate = inputDueDate();

                // 할 일 등록
                Todo<ID> registeredTodo = todoService.registerTodo(
                        new Todo<>(todo, LocalDate.parse(dueDate)));

                // 등록된 할 일 출력
                displayRegisteredTodo(registeredTodo);

                registrationCompleted = true;
            } catch (BackToPreviousMenuException ex) {
                println(ex.getMessage());
                registrationCompleted = true;
            } catch (Exception ex) {
                println(ex.getMessage());
            }
        }
    }

    // 할 일 완료 처리
    private void completeTodo() {
        // 할 일 목록 조회
        List<Todo<ID>> todoList = todoService.findAllTodos();

        if (todoList.isEmpty()) {
            println("[안내] 등록된 할 일이 없습니다.");
        } else {
            boolean isCompleted = false;

            while (!isCompleted) {
                // 완료 처리할 할 일 목록 표시
                displayTodoList(todoList);

                try {
                    // 완료 처리할 할 일 번호 선택
                    int selectedTodoId = selectTodoToComplete();

                    // 할 일 완료 처리
                    processCompleteTodo(todoList.get(selectedTodoId - 1));
                    isCompleted = true;
                } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                    println("# 등록되지 않은 할 일 번호입니다.");
                } catch (BackToPreviousMenuException ex) {
                    println(ex.getMessage());
                    isCompleted = true;
                } catch (Exception ex) {
                    println(ex.getMessage());
                }

            }
        }

    }

    private void processCompleteTodo(Todo<ID> todo) {
        todoService.completeTodo(todo, Todo.TaskState.COMPLETE);
        printf("# %s번 할 일을 완료 처리했습니다.", todo.getTodoId());
    }

    private int selectTodoToComplete() {
        println(("> 완료 처리할 할일 번호를 입력해 주세요. ex) 1"));
        return Integer.parseInt(ConsoleInputHandler.inputValue());
    }

    private void displayTodoList(List<Todo<ID>> todoList) {
        todoList.forEach(todo -> {
            // 출력. ex) 1. 잠자기(2024-09-12, 미완료)
            printf("%s. %s(%s, %s)",
                                    todo.getTodoId(),
                                    todo.getTask(),
                                    todo.getDueDate(),
                                    todo.getTaskState().getState());
        });
    }

    // 애플리케이션 종료
    private void exitApplication() {
        println("애플리케이션을 종료합니다.");
        System.exit(0);
    }

    private void displayRegisteredTodo(Todo<ID> todo) {
        println("# 할 일이 등록되었습니다!!");
        println(todo.getTask());
        println(todo.getDueDate().toString());
    }

    private String inputTodo() {
        boolean isCompleted = false;
        String todo = null;

        while (!isCompleted) {
            try {
                println("> 할 일을 입력하세요. 예) 숙제 하기");
                todo = ConsoleInputHandler.inputValue();
                validateTodo(todo);

                isCompleted = true;
            } catch (IllegalArgumentException ex) {
                println(ex.getMessage());
            }
        }

        return todo;
    }

    private void validateTodo(String todo) {
        InputValidator.validateNotBlank(todo);
    }

    private String inputDueDate() {
        boolean isCompleted = false;
        String dueDate = null;

        while (!isCompleted) {
            try {
                println("> 할 일을 수행할 날짜를 입력하세요. 예) 2024-06-10");
                dueDate = ConsoleInputHandler.inputValue();
                validateDueDate(dueDate);

                isCompleted = true;
            } catch (IllegalArgumentException ex) {
                println(ex.getMessage());
            }
        }

        return dueDate;
    }

    private void validateDueDate(String dueDate) {
        InputValidator.validateDate(dueDate, "^\\d{4}-\\d{2}-\\d{2}$");
    }
}
