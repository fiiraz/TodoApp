package com.pirozgur.todoproject_new;

import com.pirozgur.todoproject_new.todolist.model.TodoItem;
import com.pirozgur.todoproject_new.todolist.service.TodoServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.profiles.active=dev"})
public class TodoItemTest {

    @Autowired
    TodoServiceImpl todoService;

    @Test
    public void TestItem(){
        TodoItem todoItem = todoService.getById(4L);
        Assert.assertEquals(todoItem.getName(), "item1");
    }
}
