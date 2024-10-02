package ru.company.understandablepractice.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Role;
import ru.company.understandablepractice.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public Role saveRole(Role role) {
        List<Role> rolesExists = repository.findAll();

        if(!rolesExists.contains(role)) {
            return repository.save(role);
        } else return repository.findByName(role.getName());
    }
}
