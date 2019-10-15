package com.pirozgur.todoproject_new.todolist.service;

import com.pirozgur.todoproject_new.todolist.model.TodoList;
import com.pirozgur.todoproject_new.todolist.web.dto.TodoListDto;
import com.pirozgur.todoproject_new.user.model.User;

public interface TodoService {

    TodoList save(TodoListDto todoListDto, User owner);
}
