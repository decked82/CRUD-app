package com.yundenis.spring_mvc.service;

import com.yundenis.spring_mvc.dao.UserDao;
import com.yundenis.spring_mvc.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, RoleService roleService, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public List<User> showAllUsers() {
        return userDao.showAllUsers();
    }

    @Transactional
    @Override
    public void createFirstUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.createFirstUser(user);
    }

    @Transactional
    @Override
    public void saveUser(User user, String[] roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleService.getRolesByName(roles));
        userDao.saveUser(user, roles);
    }

    @Transactional
    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Transactional
    @Override
    public User getUsernameByName(String name) {
        return userDao.getUsernameByName(name);
    }

    @Transactional
    @Override
    public void updateUser(User updatedUser, String[] roles) {
        if (!updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        } else {
            updatedUser.setPassword(userDao.getUser(updatedUser.getId()).getPassword());
        }
        updatedUser.setRoles(roleService.getRolesByName(roles));
        userDao.updateUser(updatedUser, roles);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }
}
