package com.leonardo.order_system.service;

import com.leonardo.order_system.dto.UserDTO;
import com.leonardo.order_system.dto.UserRoleDTO;
import com.leonardo.order_system.entities.User;
import com.leonardo.order_system.entities.UserRole;
import com.leonardo.order_system.repositories.UserRepository;
import com.leonardo.order_system.repositories.UserRoleRepository;
import com.leonardo.order_system.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Entering in loadUserByUsername Method...");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("Username not found: {}", username);
            throw new UsernameNotFoundException("Could not found user.");
        }
        log.info("User {} authenticated successfully.", user);
        return new CustomUserDetails(user);
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findUserById(String id) {
        return userRepository.findById(Long.parseLong(id)).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found.");
        });
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<UserDTO> findAllUsersDTO() {
        List<User> users = findAllUsers();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(new UserDTO(user.getUsername(), user.getPassword()));
        }
        return userDTOS;
    }

    public User updateUser(User newUser) {
        User user = findUserById(newUser.getId().toString());
        user.setUsername(newUser.getUsername());
        user.setRoles(newUser.getRoles());
        return newUser;
    }

    public User addRole(String userId, UserRoleDTO userRoleDTO) {
        UserRole userRole = userRoleRepository.findById(userRoleDTO.getId()).orElseThrow(() -> {
            throw new RuntimeException("User role not found.");
        });
        User user = findUserById(userId);
        user.addRoles(userRole);
        userRepository.save(user);
        return user;
    }
}
