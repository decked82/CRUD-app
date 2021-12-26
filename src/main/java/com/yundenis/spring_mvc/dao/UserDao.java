package com.yundenis.spring_mvc.dao;

import com.yundenis.spring_mvc.models.User;

import java.util.List;

public interface UserDao {

    List<User> showAllUsers();

    void saveUser(User user);

    User getUser(Long id);

    User getUsernameByName(String name);

    void updateUser(User user);

    void deleteUser(Long id);
}
