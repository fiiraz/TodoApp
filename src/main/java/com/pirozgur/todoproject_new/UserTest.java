package com.pirozgur.todoproject_new;

import com.pirozgur.todoproject_new.user.model.User;
import com.pirozgur.todoproject_new.user.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.profiles.active=dev"})
public class UserTest {

    @Autowired
    UserServiceImpl userService;
    @Test
    public void TestUser(){
        User user = userService.findByEmail("ozgurpir14@gmail.com");
        Assert.assertEquals(user.getEmail(),"ozgurpir14@gmail.com");
    }
}
