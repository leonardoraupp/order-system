package com.leonardo.order_system.repositories;

import com.leonardo.order_system.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
