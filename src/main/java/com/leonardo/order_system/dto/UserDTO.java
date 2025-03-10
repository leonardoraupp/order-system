package com.leonardo.order_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDTO {
    private String username;
    private String password;
//    private Set<UserRole> roles;
}
