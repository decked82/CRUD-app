package com.yundenis.spring_mvc.dao;

import com.yundenis.spring_mvc.models.User;
import com.yundenis.spring_mvc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.security.Principal;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserDaoImpl(PasswordEncoder passwordEncoder, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public List<User> showAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        return query.getResultList();
    }

    @Override
    public void createFirstUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.merge(user);
    }

    @Override
    public void saveUser(User user, String[] roles) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleService.getRolesByName(roles));
        entityManager.persist(user);
    }

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUsernameByName(String username) {
        return entityManager.createQuery("from User where username=:name", User.class)
                .setParameter("name", username).getSingleResult();
    }

    @Override
    public void updateUser(Long id, User updatedUser, String[] roles) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        updatedUser.setRoles(roleService.getRolesByName(roles));
        entityManager.merge(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);

    }

    @Override
    public User showUser(Principal principal) {
        return entityManager.createQuery("from User where username=:username", User.class)
                .setParameter("username", principal.getName()).getResultList().get(0);
    }

}
