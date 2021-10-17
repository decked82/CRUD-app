package com.yundenis.spring_mvc.dao;

import com.yundenis.spring_mvc.models.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> getAllRoles() {
        TypedQuery<Role> query = entityManager.createQuery("from Role", Role.class);
        return query.getResultList().stream().collect(Collectors.toSet());
    }

    @Override
    public Set<Role> getRolesByName(String[] roles) {
        TypedQuery<Role> query = entityManager.createQuery("from Role r where r.role in (:roles)", Role.class)
                .setParameter("roles", Arrays.asList(roles));
        return query.getResultList().stream().collect(Collectors.toSet());
    }

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }
}
