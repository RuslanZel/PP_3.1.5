package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<User> allUsers() {
        return entityManager.createQuery("select p from User p", User.class)
                .getResultStream().collect(Collectors.toSet());
    }

    @Override
    public User findByName(String name) {
        return entityManager.createQuery("select p from User p join fetch p.roles where p.name =: name", User.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public User findByEmail(String email) {
        return entityManager.createQuery("select p from User p join fetch p.roles where p.email =: email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteUser(Long id) {
        entityManager.remove(getUser(id));
    }
}
