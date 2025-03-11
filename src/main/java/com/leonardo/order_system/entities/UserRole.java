package com.leonardo.order_system.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leonardo.order_system.dto.UserRoleDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity()
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public void removeUsers(User user) {
        users.remove(user);
        user.removeRoles(this);
    }

    public void addUsers(User user) {
        users.add(user);
        user.addRoles(this);

    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        UserRole userRole = (UserRole) object;
        return Objects.equals(id, userRole.id) && Objects.equals(name, userRole.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
