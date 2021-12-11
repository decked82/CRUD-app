package com.yundenis.spring_mvc.dao;

import com.yundenis.spring_mvc.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> showAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void createFirstUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void saveUser(User user, String[] roles) {
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
    public void updateUser(User updatedUser, String[] roles) {
        entityManager.merge(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        entityManager.remove(entityManager.find(User.class, id));

    }
}
