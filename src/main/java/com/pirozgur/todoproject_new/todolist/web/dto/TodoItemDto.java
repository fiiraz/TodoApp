package com.pirozgur.todoproject_new.todolist.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
public class TodoItemDto {

    @NotEmpty
    private String name;

    private String listname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date enddate;

}
