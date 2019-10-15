package com.pirozgur.todoproject_new.todolist.service;

import com.pirozgur.todoproject_new.todolist.model.TodoItem;
import com.pirozgur.todoproject_new.todolist.model.TodoList;
import com.pirozgur.todoproject_new.todolist.web.dto.TodoItemDto;
import com.pirozgur.todoproject_new.todolist.web.dto.TodoListDto;
import com.pirozgur.todoproject_new.todolist.repository.TodoItemRepository;
import com.pirozgur.todoproject_new.todolist.repository.TodoListRepository;
import com.pirozgur.todoproject_new.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private TodoItemRepository todoItemRepository;

    public List<TodoList> findAllByOwner (User owner){
        return todoListRepository.findAllByOwner(owner);
    }

    public List<TodoItem> findAllByTodoList (TodoList todoList){
        return todoItemRepository.findAllByList(todoList);
    }

    @Override
    public TodoList save(TodoListDto todoListDto, User owner) {
        TodoList todoList = new TodoList();
        todoList.setName(todoListDto.getName());
        todoList.setOwner(owner);
        return todoListRepository.save(todoList);
    }

    public TodoItem saveItem(TodoItemDto todoItemDto,User owner, TodoList todoList) {
        TodoItem todoItem = new TodoItem();
        todoItem.setName(todoItemDto.getName());
        todoItem.setCompleted(false);
        todoItem.setList(todoList);
        todoItem.setOwner(owner);
        todoItem.setEndDate(todoItemDto.getEnddate());
        return todoItemRepository.save(todoItem);
    }

    public TodoItem saveItemObject(TodoItem todoItem){
        return todoItemRepository.save(todoItem);
    }

    public void deleteItemById(Long id){
        todoItemRepository.deleteById(id);
    }

    public void deleteListById(Long id, User owner){
        todoListRepository.deleteByIdAndOwner(id, owner);
    }

    public TodoList findOneByName(String name, User owner){
        return todoListRepository.findOneByNameAndOwner(name, owner);
    }

//    public TodoItem findOneItemByName(String name){
//        return todoItemRepository.findOneByName(name);
//    }

    public TodoItem getById(Long id){
       return todoItemRepository.getById(id);
    }

    public List<TodoItem> findAllByListAndCompleted(Boolean completed, TodoList todoList){
        return todoItemRepository.getByCompletedAndList(completed, todoList);
    }

    public List<TodoItem> getAllByNameIsContainingAndList(String name, TodoList todoList){
        return todoItemRepository.getAllByNameIsContainingAndList(name,todoList);
    }

    public List<TodoItem> getAllByCompletedAndEndDateIsLessThan(Boolean completed, Date date){
        return todoItemRepository.getAllByCompletedAndEndDateIsLessThan(completed, date);
    }

    public List<TodoItem> getAllByListAndDependentIdIs(TodoList todoList, Long id){
        return todoItemRepository.getAllByListAndDependentIdIs(todoList, id);
    }
}
