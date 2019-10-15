package com.pirozgur.todoproject_new.todolist.web.dto;

import javax.validation.constraints.NotEmpty;

public class TodoListDto {

    @NotEmpty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
