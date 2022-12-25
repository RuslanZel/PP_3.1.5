package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepositoryImpl;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepositoryImpl roleRepositoryImpl;

    public RoleServiceImpl(RoleRepositoryImpl roleRepositoryImpl) {
        this.roleRepositoryImpl = roleRepositoryImpl;
    }

    @Override
    public Set<Role> allRoles() {
        return roleRepositoryImpl.findAll();
    }
}
