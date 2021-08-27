package com.yundenis.spring_mvc.service;

import com.yundenis.spring_mvc.models.User;

import java.util.List;

public interface UserService {

    public List<User> showAllUsers();

    public void saveUser(User user);

    public User getUser(int id);

    public void updateUser(int id, User user);

    public void deleteUser(int id);
}
