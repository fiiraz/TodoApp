package com.pirozgur.todoproject_new.todolist.web.dto;

import com.pirozgur.todoproject_new.todolist.model.TodoItem;

import java.util.List;

public class ShowTodoListDto {

    private String name;

    private List<TodoItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TodoItem> getItems() {
        return items;
    }

    public void setItems(List<TodoItem> items) {
        this.items = items;
    }
}
