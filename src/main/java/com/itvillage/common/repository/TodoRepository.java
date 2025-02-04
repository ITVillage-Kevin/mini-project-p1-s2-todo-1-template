package com.itvillage.common.repository;

import com.itvillage.common.entity.Todo;

import java.util.List;

public interface TodoRepository<ID> {
    Todo<ID> save(Todo<ID> todo);
    List<Todo<ID>> findAll();
    Todo<ID> findByTodo(Todo<ID> todo);
}
