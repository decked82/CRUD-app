package com.yundenis.spring_mvc.service;

import com.yundenis.spring_mvc.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    public Set<Role> getAllRoles();

    public void saveRole(Role role);
}
