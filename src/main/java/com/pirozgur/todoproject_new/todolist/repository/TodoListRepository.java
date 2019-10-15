package com.pirozgur.todoproject_new.todolist.repository;

import com.pirozgur.todoproject_new.todolist.model.TodoList;
import com.pirozgur.todoproject_new.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoListRepository extends CrudRepository<TodoList, Long> {
    List<TodoList> findAllByOwner(User owner);

    TodoList findOneByIdAndOwner(Long id, User owner);

    TodoList findOneByNameAndOwner(String name, User owner);

    @Transactional
    void deleteByIdAndOwner(Long id, User owner);
}
