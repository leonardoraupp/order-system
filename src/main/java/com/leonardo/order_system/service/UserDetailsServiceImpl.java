package com.leonardo.order_system.service;

import com.leonardo.order_system.entities.User;
import com.leonardo.order_system.repositories.UserRepository;
import com.leonardo.order_system.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Entering in loadUserByUsername Method...");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("Username not found: {}", username);
            throw new UsernameNotFoundException("Could not found user.");
        }
        log.info("User authenticated successfully.");
        return new CustomUserDetails(user);
    }

    public User registerUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
