package com.yundenis.spring_mvc.dao;

import com.yundenis.spring_mvc.models.Role;

import java.util.Set;

public interface RoleDao {

    Set<Role> getAllRoles();

    Set<Role> getRolesByName(String[] roles);

    void saveRole(Role role);

}
