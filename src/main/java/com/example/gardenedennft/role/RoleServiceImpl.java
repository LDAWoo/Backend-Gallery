package com.example.gardenedennft.role;

import com.example.gardenedennft.exception.ResourceDuplicateException;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    private final RoleDTOMapper roleDTOMapper;

    @Override
    public void addRole(RoleRequest request) {
        checkExistRoleByCode(request.getCode());

        roleRepo.save(
                Role.builder()
                        .name(request.getName().trim().toUpperCase())
                        .code(request.getCode().trim().toLowerCase())
                        .build()
        );
    }

    @Override
    public void updateRole(RoleRequest request, UUID rid) {
        Role role = extractedRoleById(rid);

        checkExistRoleByCode(request.getCode());

        if (!role.getName().equals(request.getName()))
            role.setName(request.getName());

        if (!role.getCode().equals(request.getCode()))
            role.setCode(request.getCode());

        roleRepo.save(role);
    }

    @Override
    public void deleteRole(UUID uuid) {
        Role role = extractedRoleById(uuid);
        roleRepo.delete(role);
    }

    @Override
    public RoleDTO findRoleByCode(String code) {
        return roleRepo.findRoleByCode(code)
                .map(roleDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("find role by code :" + code + " not found!."));
    }

    @Override
    public List<RoleDTO> findRolesByName(String name) {
        List<Role> roles = roleRepo.findRolesByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("find role by name :" + name + " not found!."));

        return roles.stream()
                .map(roleDTOMapper)
                .toList();
    }

    @Override
    public RoleResponse findAllRole() {
        return RoleResponse.builder()
                .listResult(
                        roleRepo.findAll()
                        .stream()
                        .map(roleDTOMapper)
                        .toList())
                .build();
    }

    @Override
    public boolean existsByCode(String code) {
        return roleRepo.existsByCode(code);
    }

    private void checkExistRoleByCode(String code) {
        if (existsByCode(code)) {
            throw new ResourceDuplicateException("role " + code + " already exist!.");
        }
    }

    private Role extractedRoleById(UUID uuid) {
        return roleRepo.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("find role by id " + uuid + " not found!."));
    }
}
