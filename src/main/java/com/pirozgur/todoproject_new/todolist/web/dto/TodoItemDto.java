package com.pirozgur.todoproject_new.todolist.web.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class TodoItemDto {

    @NotEmpty
    private String name;

    private String listname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date enddate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
}
