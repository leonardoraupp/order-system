package com.leonardo.order_system.service;

import com.leonardo.order_system.entities.User;
import com.leonardo.order_system.entities.UserRole;
import com.leonardo.order_system.repositories.UserRepository;
import com.leonardo.order_system.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole create(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    public UserRole getById(String id) {
        Long roleId = Long.parseLong(id);
        return userRoleRepository.findById(roleId).orElseThrow(() -> {
            throw new RuntimeException("User role not found.");
        });
    }
}
