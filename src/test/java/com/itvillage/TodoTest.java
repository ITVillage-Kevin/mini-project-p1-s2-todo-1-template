package com.itvillage;

import com.itvillage.common.entity.Todo;
import com.itvillage.common.exception.AlreadyCompletedException;
import com.itvillage.common.exception.PastDueDateException;
import com.itvillage.common.repository.InMemoryTodoRepository;
import com.itvillage.common.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Todo 도메인 로직 테스트")
public class TodoTest {
    @DisplayName("dueDate이 현재 날짜보다 과거 날짜일 경우, 예외가 발생해야 한다.")
    @Test
    public void should_throw_PastDueDateException_When_past_dueDate() {
        // given
        // 시간이 지나면 과거 날짜가 되므로 에러가 발생할 수 있다.
//        Todo todo = new Todo("홍대에서 저녁 먹기", LocalDate.of(2024, 5, 20));
        Todo<Integer> todo =
                new Todo<>("홍대에서 저녁 먹기",
                        LocalDate.of(
                                LocalDate.now().getYear(),
                                LocalDate.now().getMonth().minus(1),
                                LocalDate.now().getDayOfMonth()));

        // when

        // then
        assertThrows(PastDueDateException.class, todo::verifyValidDueDate);
    }

    // 이미 완료 처리된 할 일은 다시 완료 처리 할 수 없어야 한다.
    @DisplayName("이미 완료 처리된 할 일은 다시 완료 처리하면 예외가 발생해야 한다.")
    @Test
    public void should_throw_AlreadyCompletedException_When_complete_task_again() {
        // given
        Todo<Integer> todo =
                new Todo<>("홍대에서 저녁 먹기",
                        LocalDate.of(
                                LocalDate.now().getYear(),
                                LocalDate.now().getMonth(),
                                LocalDate.now().getDayOfMonth()).plusDays(5));

        // when, then
        todo.completeTask(Todo.TaskState.COMPLETE);
        assertThrows(AlreadyCompletedException.class,
                () -> todo.completeTask(Todo.TaskState.COMPLETE));
    }
}
