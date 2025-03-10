package com.leonardo.order_system.controller;

import com.leonardo.order_system.dto.UserDTO;
import com.leonardo.order_system.dto.UserRoleDTO;
import com.leonardo.order_system.entities.User;
import com.leonardo.order_system.service.UserDetailsServiceImpl;
import com.leonardo.order_system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/{id}/roles")
    public ResponseEntity<String> addUserRole(@PathVariable String id, @RequestBody UserRoleDTO roleDTO) {
        userDetailsService.addRole(id, roleDTO);
        return ResponseEntity.ok("Role added successfully.");
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userDetailsService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
        User user = userDetailsService.findUserById(id);
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(userDTO);
    }
}
