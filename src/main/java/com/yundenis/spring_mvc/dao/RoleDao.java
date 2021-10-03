package com.yundenis.spring_mvc.dao;

import com.yundenis.spring_mvc.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {

    public Set<Role> getAllRoles();

    public void saveRole(Role role);

}
