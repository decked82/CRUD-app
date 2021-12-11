package com.yundenis.spring_mvc.service;

import com.yundenis.spring_mvc.models.Role;

import java.util.Set;

public interface RoleService {

    Set<Role> getAllRoles();

    Set<Role> getRolesByName(String[] roles);

    void saveRole(Role role);
}
