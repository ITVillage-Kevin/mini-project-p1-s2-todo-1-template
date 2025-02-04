package com.itvillage.common.repository;

import com.itvillage.common.entity.Todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTodoRepository implements TodoRepository<Integer> {
    private final Map<Integer, Todo<Integer>> todoMap = new HashMap<>();

    //  등록할 할 일을 저장 or 등록된 할 일 업데이트
    public Todo<Integer> save(Todo<Integer> todo) {
        if (isNew(todo)) {
            int maxId = todoMap.size();
            int todoId = maxId + 1;

            todo.addTodoId(todoId);

            todoMap.put(todoId, todo);
        } else {
            // 할 일을 업데이트
            todoMap.put(todo.getTodoId(), todo);
        }


        return todo;
    }

    private boolean isNew(Todo<Integer> todo) {
        return todo.getTodoId() == null;
    }

    public List<Todo<Integer>> findAll() {
        return new ArrayList<>(todoMap.values());
    }

    public Todo<Integer> findByTodo(Todo<Integer> todo) {
        int todoId = todo.getTodoId();
        if (todoMap.containsKey(todoId)) {
            return todoMap.get(todoId);
        }
        throw new RuntimeException("Todo task is not found");
    }
}
