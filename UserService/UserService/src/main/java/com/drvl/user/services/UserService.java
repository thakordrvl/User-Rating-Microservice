package com.drvl.user.services;

import com.drvl.user.entities.User;

import java.util.List;

public interface UserService {


    User saveUser(User user);

    List<User> getAllUser();

    User getUser(String userId);

}
