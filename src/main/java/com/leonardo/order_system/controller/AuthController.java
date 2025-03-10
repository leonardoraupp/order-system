package com.leonardo.order_system.controller;

import com.leonardo.order_system.dto.UserDTO;
import com.leonardo.order_system.entities.User;
import com.leonardo.order_system.security.JwtService;
import com.leonardo.order_system.service.UserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private UserDetailsServiceImpl userServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        User response = userServiceImpl.registerUser(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body("User created successfully.");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody UserDTO request) {
        try {
            log.info("Authenticating  user: {}", request.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password.");
        } catch (RuntimeException e) {
            log.error("Runtime exception during authentication: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        log.info("Generating JWT for  user: {}", request.getUsername());
        final UserDetails userDetails = userServiceImpl.loadUserByUsername(request.getUsername());
        final String jwt = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }
}
