package com.example.gardenedennft.role;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    void addRole(RoleRequest request);

    void updateRole(RoleRequest request, UUID rid);

    void deleteRole(UUID rid);

    RoleDTO findRoleByCode(String code);

    List<RoleDTO> findRolesByName(String name);

    RoleResponse findAllRole();

    boolean existsByCode(String code);
}
