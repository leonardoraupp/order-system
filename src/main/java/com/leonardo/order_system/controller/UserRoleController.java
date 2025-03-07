package com.leonardo.order_system.controller;

import com.leonardo.order_system.entities.UserRole;
import com.leonardo.order_system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/roles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody UserRole role) {
        UserRole response = userRoleService.create(role);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(uri).body("Role created successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRole> getRole(@PathVariable String id) {
        UserRole role = userRoleService.getById(id);
        return ResponseEntity.ok(role);
    }
}
