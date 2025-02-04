package com.itvillage.common.entity;

import com.itvillage.common.exception.AlreadyCompletedException;
import com.itvillage.common.exception.PastDueDateException;

import java.time.LocalDate;

// 도메인 엔티티 클래스
public class Todo<ID> {
    private ID todoId;
    private final String task;
    private final LocalDate dueDate;
    private TaskState taskState = TaskState.INCOMPLETE;

    public Todo(String task, LocalDate dueDate) {
        this.task = task;
        this.dueDate = dueDate;
    }

    public void addTodoId(ID todoId) {
        this.todoId = todoId;
    }

    public void verifyValidDueDate() {
        // 도메인 규칙: 할 일 수행 날짜는 현재 날짜보다 크거나 같아야 한다.
        LocalDate currentDate = LocalDate.now();

        if (this.dueDate.isBefore(currentDate)) {
            throw new PastDueDateException();
        }
    }

    public void completeTask(TaskState taskState) {
        verifyTaskState();
        this.taskState = taskState;
    }

    private void verifyTaskState() {
        // 도메인 규칙: 이미 완료 처리된 할 일을 다시 완료 처리 상태로 변경할 수 없다.
        if (this.taskState == Todo.TaskState.COMPLETE) {
            throw new AlreadyCompletedException();
        }
    }

    public ID getTodoId() {
        return todoId;
    }

    public String getTask() {
        return task;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public enum TaskState {
        COMPLETE("완료"),
        INCOMPLETE("미완료");

        private final String state;

        TaskState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }
    }
}
