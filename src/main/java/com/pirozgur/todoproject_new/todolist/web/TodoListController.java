package com.pirozgur.todoproject_new.todolist.web;

import com.pirozgur.todoproject_new.todolist.model.*;
import com.pirozgur.todoproject_new.todolist.web.dto.FilterDto;
import com.pirozgur.todoproject_new.todolist.web.dto.SetDependencyDto;
import com.pirozgur.todoproject_new.todolist.web.dto.ShowTodoListDto;
import com.pirozgur.todoproject_new.todolist.service.TodoServiceImpl;
import com.pirozgur.todoproject_new.user.model.User;
import com.pirozgur.todoproject_new.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class TodoListController {

    @Autowired
    private TodoServiceImpl todoServiceImpl;

    @Autowired
    private UserServiceImpl userService;


    private User getOwnerFromAuthentication(Authentication authentication) {
        return userService.findByEmail(authentication.getName());
    }

    @GetMapping("/lists")
    public ModelAndView getLists(Authentication authentication){
        deadlineItems();
        ModelAndView model = new ModelAndView();
        List<TodoList> todoLists = todoServiceImpl.findAllByOwner(getOwnerFromAuthentication(authentication));
        List<ShowTodoListDto> showTodoListDtos = new ArrayList<>();
        int size = todoLists.size();
        for (int i=0; i< size; i++){
            ShowTodoListDto showTodoListDto = new ShowTodoListDto();
            showTodoListDto.setName(todoLists.get(i).getName());
            List<TodoItem> todoItemList = todoServiceImpl.findAllByTodoList(todoLists.get(i));
            if(!todoItemList.isEmpty()){
                showTodoListDto.setItems(todoItemList);
            }
            showTodoListDtos.add(showTodoListDto);
        }

        model.addObject("list", showTodoListDtos);
        model.setViewName("/todo/lists");
        return model;
    }

    @RequestMapping( value = "/delete/{id}" , method = RequestMethod.GET)
    public String deleteItem(@PathVariable long id, @RequestParam(defaultValue = "") String list) {

        TodoItem todoItem1 = todoServiceImpl.getById(id);
        TodoItem todoItem2 = todoServiceImpl.getById(todoItem1.getDependentId());

        if(todoServiceImpl.getById(id).getDependentId() == 0){
            todoServiceImpl.deleteItemById(id);
        } else if(todoItem2.getCompleted()){
            todoServiceImpl.deleteItemById(todoItem1.getId());
            todoItem2.setDependentId(0L);
            todoServiceImpl.saveItemObject(todoItem2);
        }

        if(!list.isEmpty()){
            return "redirect:/todo/showlist/" + list;
        }
        return "redirect:/todo/lists";
    }

    @RequestMapping( value = "/deletelist/{name}" , method = RequestMethod.GET)
    public String deleteList(@PathVariable String name, Authentication authentication) {
        TodoList todoList = todoServiceImpl.findOneByName(name, getOwnerFromAuthentication(authentication));
        todoServiceImpl.deleteListById(todoList.getId(), getOwnerFromAuthentication(authentication));
        return "redirect:/todo/lists";
    }

    @RequestMapping( value = "/complete/{id}" , method = RequestMethod.GET)
    public String completeItem(@PathVariable long id, @RequestParam(defaultValue = "") String list) {
        TodoItem todoItem = todoServiceImpl.getById(id);
        todoItem.setCompleted(true);
        todoServiceImpl.saveItemObject(todoItem);
        if(!list.isEmpty()){
            return "redirect:/todo/showlist/" + list;
        }else {
            return "redirect:/todo/lists";
        }
    }

    @GetMapping("/showlist/{name}")
    public ModelAndView showList(@PathVariable String name,
                                 @RequestParam(defaultValue = "") String completed,
                                 Authentication authentication) {
        ModelAndView model = new ModelAndView();
        ShowTodoListDto showTodoListDto = new ShowTodoListDto();
        showTodoListDto.setName(name);
        List<TodoItem> todoItemList = new ArrayList<>();
        if(completed.isEmpty()){
                try {
                    todoItemList = todoServiceImpl.findAllByTodoList(todoServiceImpl.findOneByName(name, getOwnerFromAuthentication(authentication)));
                }catch (Exception e){
                }
        } else if(completed.equals("1")){
                todoItemList = todoServiceImpl.findAllByListAndCompleted(true,(todoServiceImpl.findOneByName(name, getOwnerFromAuthentication(authentication))));
        } else {
            try {
                todoItemList = todoServiceImpl.findAllByListAndCompleted(false,(todoServiceImpl.findOneByName(name, getOwnerFromAuthentication(authentication))));
            }catch (Exception e){
            }
        }

        if(!todoItemList.isEmpty()){
            showTodoListDto.setItems(todoItemList);
        }
        model.addObject("list", showTodoListDto);
        model.setViewName("/todo/todolist");
        return model;
    }

    @ModelAttribute("filtertext")
    public FilterDto filterTextDto() {
        return new FilterDto();
    }

    @PostMapping(value = "/filter/{name}")
    public ModelAndView filterText(@PathVariable String name,
                                   @ModelAttribute("filtertext") @Valid FilterDto filterDto,
                                   Authentication authentication,
                                   BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        ShowTodoListDto showTodoListDto = new ShowTodoListDto();
        showTodoListDto.setName(name);
        List<TodoItem> todoItemList = new ArrayList<>();
        System.out.println("**"+filterDto.getText());

            todoItemList = todoServiceImpl.getAllByNameIsContainingAndList(filterDto.getText(), (todoServiceImpl.findOneByName(name, getOwnerFromAuthentication(authentication))));
        System.out.println(todoItemList.size());

        if(!todoItemList.isEmpty()){
            showTodoListDto.setItems(todoItemList);
        }
        model.addObject("list", showTodoListDto);
        model.setViewName("/todo/todolist");
        return model;
    }

    @GetMapping("/getdependency/{list}/{item}")
    public ModelAndView showAddTodoListForm(
            @PathVariable String list,
            @PathVariable Long item,
            Authentication authentication
                                            ) {
        ModelAndView model = new ModelAndView();
        TodoItem todoItem = todoServiceImpl.getById(item);
        model.addObject("itemOne", todoItem);
        List<TodoItem> todoItemList = todoServiceImpl.getAllByListAndDependentIdIs((todoServiceImpl.findOneByName(list, getOwnerFromAuthentication(authentication))), 0L);
        try {
            todoItemList.remove(todoItemList.indexOf(todoItem));
        }catch (Exception e){
        }

        model.addObject("otherItems",todoItemList);
        model.setViewName("/todo/adddependency");
        return model;
    }

    @ModelAttribute("dependent")
    public SetDependencyDto dependencyTextDto() {
        return new SetDependencyDto();
    }

    @PostMapping(value = "/dependency/{id}")
    public String filterText(@ModelAttribute("dependent") @Valid SetDependencyDto setDependencyDto,
                                   @PathVariable Long id,
                                   Authentication authentication,
                                   BindingResult bindingResult) {
        TodoItem todoItem1 = todoServiceImpl.getById(id);
        TodoItem todoItem2 = todoServiceImpl.getById(setDependencyDto.getItemTwoId());

        if(todoItem1.getDependentId() == 0 && todoItem2.getDependentId() == 0){
            todoItem1.setDependentId(setDependencyDto.getItemTwoId());
            todoItem2.setDependentId(id);
            todoServiceImpl.saveItemObject(todoItem1);
            todoServiceImpl.saveItemObject(todoItem2);
        }
        return "redirect:/todo/lists";
    }

    @GetMapping(value = "/setindependent/{id}")
    public String setIndependent(
            @PathVariable Long id,
            Authentication authentication){

        TodoItem todoItem1 = todoServiceImpl.getById(id);
        TodoItem todoItem2 = todoServiceImpl.getById(todoItem1.getDependentId());

        todoItem1.setDependentId(0L);
        todoItem2.setDependentId(0L);
        todoServiceImpl.saveItemObject(todoItem1);
        todoServiceImpl.saveItemObject(todoItem2);

        return "redirect:/todo/lists";
    }

    public void deadlineItems(){
        Date date = new Date();
        List<TodoItem> todoItemList =  todoServiceImpl.getAllByCompletedAndEndDateIsLessThan(false, date);
        todoItemList.forEach(item->{
            item.setCompleted(true);
            todoServiceImpl.saveItemObject(item);
                });
    }
}
