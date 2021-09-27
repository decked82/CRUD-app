package com.yundenis.spring_mvc.service;

import com.yundenis.spring_mvc.models.User;

import java.security.Principal;
import java.util.List;

public interface UserService {

    public List<User> showAllUsers();

    public void saveUser(User user, String[] roles);

    public User getUser(Long id);

    public User getUserByName(String name);

    public void updateUser(Long id, User user, String[] roles);

    public void deleteUser(Long id);

    public User showUser(Principal principal);
}
