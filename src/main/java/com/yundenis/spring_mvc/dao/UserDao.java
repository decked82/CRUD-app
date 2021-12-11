package com.yundenis.spring_mvc.dao;

import com.yundenis.spring_mvc.models.User;

import java.util.List;

public interface UserDao {

    List<User> showAllUsers();

    void createFirstUser(User user);

    void saveUser(User user, String[] roles);

    User getUser(Long id);

    User getUsernameByName(String name);

    void updateUser(User user, String[] roles);

    void deleteUser(Long id);
}
