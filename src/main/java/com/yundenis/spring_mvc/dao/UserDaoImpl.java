package com.yundenis.spring_mvc.dao;

import com.yundenis.spring_mvc.models.Role;
import com.yundenis.spring_mvc.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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

        Set<Role> selectedRoles = new HashSet<>(Arrays.stream(roles).map(role->entityManager
                .createQuery("from Role where role=:role", Role.class)
                .setParameter("role", role).getResultList().get(0)).collect(Collectors.toList()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(selectedRoles);
        entityManager.merge(user);
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
        User userToBeUpdated = entityManager.find(User.class, id);
        userToBeUpdated.setUsername(updatedUser.getUsername());
        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setSurname(updatedUser.getSurname());
        userToBeUpdated.setAge(updatedUser.getAge());
        userToBeUpdated.setEmail(updatedUser.getEmail());

        if (!updatedUser.getPassword().isEmpty()) {
            if (!passwordEncoder.matches(passwordEncoder.encode(updatedUser.getPassword()), userToBeUpdated.getPassword())) {
                userToBeUpdated.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
        }

        Set<Role> selectedRoles = new HashSet<>(Arrays.stream(roles).map(role->entityManager
                .createQuery("from Role where role=:role", Role.class)
                .setParameter("role", role).getResultList().get(0)).collect(Collectors.toList()));
        userToBeUpdated.setRoles(selectedRoles);
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
