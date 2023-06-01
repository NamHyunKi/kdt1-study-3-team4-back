package com.example.demo.Account.repository;

import com.example.demo.Account.entity.Role;
import com.example.demo.Account.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
