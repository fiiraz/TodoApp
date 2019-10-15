package com.pirozgur.todoproject_new.user.service;


import com.pirozgur.todoproject_new.user.model.User;
import com.pirozgur.todoproject_new.user.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
