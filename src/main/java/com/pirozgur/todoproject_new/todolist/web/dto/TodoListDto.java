package com.pirozgur.todoproject_new.todolist.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TodoListDto {

    @NotEmpty
    private String name;

}
