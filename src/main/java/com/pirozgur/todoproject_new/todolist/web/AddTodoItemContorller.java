package com.pirozgur.todoproject_new.todolist.web;

import com.pirozgur.todoproject_new.todolist.model.TodoList;
import com.pirozgur.todoproject_new.todolist.service.TodoServiceImpl;
import com.pirozgur.todoproject_new.todolist.web.dto.TodoItemDto;
import com.pirozgur.todoproject_new.user.model.User;
import com.pirozgur.todoproject_new.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AddTodoItemContorller {

    @Autowired
    private TodoServiceImpl todoServiceImpl;

    @Autowired
    private UserService userService;

    private User getOwnerFromAuthentication(Authentication authentication) {
        return userService.findByEmail(authentication.getName());
    }

    @ModelAttribute("todoitem")
    public TodoItemDto todoItemRegisterDto() {
        return new TodoItemDto();
    }

    @GetMapping(value = "/additem/{name}")
    public ModelAndView addItem(@PathVariable String name, Model m) {
        ModelAndView model = new ModelAndView();
        model.addObject("list_name",name);
        m.addAttribute("itemdto", new TodoItemDto());
        model.setViewName("/todo/additem");
        return model;
    }

    @PostMapping(value = "/addnewitem/{name}")
    public String addTodoItem(@PathVariable String name, @ModelAttribute("todoitem") @Valid TodoItemDto todoItemDto, Authentication authentication, BindingResult bindingResult) {
        TodoList todoList = todoServiceImpl.findOneByName(name, getOwnerFromAuthentication(authentication));
        todoServiceImpl.saveItem(todoItemDto, getOwnerFromAuthentication(authentication), todoList);
        return "redirect:/todo/lists";
    }

}
