package com.leonardo.order_system.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leonardo.order_system.dto.UserRoleDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRole {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public UserRole(UserRoleDTO userRoleDTO) {
        this.id = userRoleDTO.getId();
        this.name = userRoleDTO.getName();
    }
}
