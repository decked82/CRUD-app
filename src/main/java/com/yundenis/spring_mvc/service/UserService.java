package com.yundenis.spring_mvc.service;

import com.yundenis.spring_mvc.models.User;

import java.util.List;

public interface UserService {

    public List<User> showAllUsers();

    public void saveUser(User user);

    public User getUser(Long id);

    public void updateUser(Long id, User user);

    public void deleteUser(Long id);
}
