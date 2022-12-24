package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Set;

@Repository
public interface UserRepository {

    Set<User> allUsers();

    User findByName(String name);

    void addUser(User user);

    void updateUser(User user);

    User getUser(Long id);

    void deleteUser(Long id);
}