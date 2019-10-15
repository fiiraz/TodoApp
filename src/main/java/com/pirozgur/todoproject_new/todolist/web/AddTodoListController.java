package com.pirozgur.todoproject_new.todolist.web;

import com.pirozgur.todoproject_new.todolist.model.TodoList;
import com.pirozgur.todoproject_new.todolist.service.TodoServiceImpl;
import com.pirozgur.todoproject_new.todolist.web.dto.TodoItemDto;
import com.pirozgur.todoproject_new.todolist.web.dto.TodoListDto;
import com.pirozgur.todoproject_new.user.model.User;
import com.pirozgur.todoproject_new.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/addlist")
public class AddTodoListController {

    @Autowired
    private TodoServiceImpl todoServiceImpl;

    @Autowired
    private UserService userService;

    private User getOwnerFromAuthentication(Authentication authentication) {
        return userService.findByEmail(authentication.getName());
    }

    @ModelAttribute("todo")
    public TodoListDto todoListDto() {
        return new TodoListDto();
    }

    @GetMapping
    public ModelAndView showAddTodoListForm() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/todo/addlist");
        return model;
    }

    @PostMapping
    public String addTodoList(TodoListDto todoListDto, Authentication authentication) {
        todoServiceImpl.save(todoListDto, getOwnerFromAuthentication(authentication));
        return "redirect:addlist?success";
    }


}
