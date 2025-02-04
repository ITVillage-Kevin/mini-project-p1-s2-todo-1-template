package com.itvillage;


import com.itvillage.common.entity.Todo;
import com.itvillage.common.exception.AlreadyCompletedException;
import com.itvillage.common.exception.PastDueDateException;
import com.itvillage.common.repository.InMemoryTodoRepository;
import com.itvillage.common.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("TodoService 테스트")
public class TodoServiceTest {
    @DisplayName("유효한 할 일 정보는 정상적으로 등록되어야 한다.")
    @Test
    public void should_register_successfully_When_valid_todo() {
        // given: 테스트에 필요한 어떤 데이터. 입력 값을 의미.
        // 시간이 지나면 과거 날짜가 되므로 에러가 발생할 수 있다.
//        Todo todo = new Todo("홍대에서 저녁 먹기", LocalDate.of(2024, 7, 20));
        Todo<Integer> todo =
                new Todo<>("홍대에서 저녁 먹기",
                        LocalDate.of(
                                    LocalDate.now().getYear(),
                                    LocalDate.now().getMonth(),
                                    LocalDate.now().getDayOfMonth()).plusDays(5));

        // when: 테스트 대상 메서드. 동작.
        TodoService<Integer> todoService = new TodoService<>(new InMemoryTodoRepository());
        Todo<Integer> registeredTodo = todoService.registerTodo(todo);

        // then: 이렇게 동작을 해야 돼. Assertion.
//        assert(registeredTodo != null);
//        assert(registeredTodo.getTask().equals("홍대에서 저녁 먹기"));
        assertThat(registeredTodo, is(notNullValue()));
        assertThat(registeredTodo.getTask(), is(equalTo(todo.getTask())));
    }

    // 미완료 상태인 할 일을 완료 상태로 업데이트 할 수 있어야 한다.
    @DisplayName("미완료 상태인 할 일을 완료 상태로 업데이트 할 수 있어야 한다.")
    @Test
    public void should_update_Incomplete_Task_State_To_Complete() {
        // given
        Todo<Integer> todo =
                new Todo<>("홍대에서 저녁 먹기",
                        LocalDate.of(
                                LocalDate.now().getYear(),
                                LocalDate.now().getMonth(),
                                LocalDate.now().getDayOfMonth()).plusDays(5));

        TodoService<Integer> todoService = new TodoService<>(new InMemoryTodoRepository());
        Todo<Integer> registeredTodo = todoService.registerTodo(todo);

        // when
        todoService.completeTodo(registeredTodo, Todo.TaskState.COMPLETE);

        Todo<Integer> updatedTodo = todoService.findTodoBy(registeredTodo);

        // then
        assertThat(updatedTodo.getTaskState(), is(equalTo(Todo.TaskState.COMPLETE)));
    }
}
