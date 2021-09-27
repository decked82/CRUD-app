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
    public void saveUser(User user, String[] roles) {
        List<Role> listAllRoles = new ArrayList<>();
        for(String role:roles) {
            List<Role> listRoles = entityManager.createQuery("from Role where role=:role", Role.class)
                    .setParameter("role", role).getResultList();
            listAllRoles.add(listRoles.get(0));
        }
        Set<Role> selectedRoles = new HashSet<>(listAllRoles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(selectedRoles);
        entityManager.persist(user);
    }

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByName(String name) {
        return entityManager.createQuery("from User where username=:name", User.class)
                .setParameter("name", name).getSingleResult();
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

        List<Role> listAllRoles = new ArrayList<>();
        for(String role:roles) {
            List<Role> listRoles = entityManager.createQuery("from Role where role=:role", Role.class)
                    .setParameter("role", role).getResultList();
            listAllRoles.add(listRoles.get(0));
        }
        Set<Role> selectedRoles = new HashSet<>(listAllRoles);
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

//    @PostConstruct
//    public void init() {
//        TypedQuery<Role> query = entityManager.createQuery("from Role", Role.class);
//        Set<List<Role>> roles = new HashSet<>();
//        roles.add(query.getResultList());
//    }
}
