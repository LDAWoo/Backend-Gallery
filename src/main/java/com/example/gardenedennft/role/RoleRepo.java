package com.example.gardenedennft.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    Optional<Role> findRoleByCode(String code);

    Optional<List<Role>> findRolesByName(String name);

    boolean existsByCode(String code);

}
