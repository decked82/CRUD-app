package com.yundenis.spring_mvc.dao;

import com.yundenis.spring_mvc.models.Role;
import com.yundenis.spring_mvc.models.User;
import com.yundenis.spring_mvc.service.RoleService;
import com.yundenis.spring_mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class CreaterRoleAndUser {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public CreaterRoleAndUser(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void initialize() {

        roleService.saveRole(new Role("ROLE_ADMIN"));
        roleService.saveRole(new Role("ROLE_USER"));
        User user = new User(1L,"Админ", "Админов", "1", 30, "admin@gmail.com",
                "admin", Collections.singleton(new Role(1L, "ROLE_ADMIN")));
        userService.createFirstUser(user);
    }
}
