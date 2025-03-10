package com.leonardo.order_system.service;

import com.leonardo.order_system.dto.UserRoleDTO;
import com.leonardo.order_system.entities.User;
import com.leonardo.order_system.entities.UserRole;
import com.leonardo.order_system.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole create(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    public UserRole findById(Long id) {
        return userRoleRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("User role not found.");
        });
    }

    public List<UserRole> getAll() {
        return userRoleRepository.findAll();
    }

    public void updateUserRole(Long id, UserRole userRole) {
        UserRole role = findById(id);
        role.setName(userRole.getName());
        role.setUsers(userRole.getUsers());
        userRoleRepository.save(role);
    }

    public void deleteUserRole(Long id) {
        Optional<UserRole> role = userRoleRepository.findById(id);
        userRoleRepository.delete(role.get());
    }

    public void addUser(User user, UserRoleDTO userRoleDTO) {
        UserRole userRole = findById(userRoleDTO.getId());
        userRole.getUsers().add(user);
        userRoleRepository.save(userRole);
    }
}
