package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Set;

@Repository
public interface UserRepository {

    Set<User> allUsers();

    User findByName(String name);


    User findByEmail(String email);

    void addUser(User user);

    void updateUser(User user);

    User getUser(Long id);

    void deleteUser(Long id);
}