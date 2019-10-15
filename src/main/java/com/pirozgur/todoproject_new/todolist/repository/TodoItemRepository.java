package com.pirozgur.todoproject_new.todolist.repository;

import com.pirozgur.todoproject_new.todolist.model.TodoItem;
import com.pirozgur.todoproject_new.todolist.model.TodoList;
import com.pirozgur.todoproject_new.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {

    List<TodoItem> findAllByList(TodoList todoList);

    List<TodoItem> getByCompletedAndList(Boolean completed, TodoList todoList);
    List<TodoItem> getAllByNameIsContainingAndList(String name, TodoList todoList);
    List<TodoItem> getAllByCompletedAndEndDateIsLessThan(Boolean completed, Date date);
    List<TodoItem> getAllByListAndDependentIdIs(TodoList todoList, Long id);

    TodoItem getById(Long id);

    TodoItem findOneByName(String name);

    TodoItem findOneByIdAndListAndOwner(Long id, TodoList todoList, User owner);

    @Transactional
    void deleteByIdAndListAndOwner(Long id, TodoList todoList, User owner);
}

