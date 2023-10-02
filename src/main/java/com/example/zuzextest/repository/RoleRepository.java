package com.example.zuzextest.repository;

import com.example.zuzextest.entity.Role;
import com.example.zuzextest.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
