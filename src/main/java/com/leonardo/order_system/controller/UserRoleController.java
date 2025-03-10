package com.leonardo.order_system.controller;

import com.leonardo.order_system.dto.UserRoleDTO;
import com.leonardo.order_system.entities.UserRole;
import com.leonardo.order_system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping
    public ResponseEntity<String> createUserRole(@RequestBody UserRoleDTO role) {
        UserRole response = userRoleService.create(new UserRole(role));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(uri).body("Role created successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRole> getUserRole(@PathVariable String id) {
        UserRole role = userRoleService.findById(Long.valueOf(id));
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity<List<UserRole>> getUserRoles() {
        List<UserRole> roles = userRoleService.getAll();
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserRole(@PathVariable Long id, @RequestBody UserRoleDTO userRoleDTO) {
        userRoleService.updateUserRole(id, new UserRole(userRoleDTO));
        return ResponseEntity.ok("User role updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long id) {
        userRoleService.deleteUserRole(id);
        return ResponseEntity.noContent().build();
    }
}
