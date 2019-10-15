package com.pirozgur.todoproject_new.todolist.web.dto;

import com.pirozgur.todoproject_new.todolist.model.TodoItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShowTodoListDto {

    private String name;

    private List<TodoItem> items;

}
