package com.example.gardenedennft.role;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class RoleDTOMapper implements Function<Role, RoleDTO> {

    private final ModelMapper mapper;

    @Override
    public RoleDTO apply(Role role) {
        return mapper.map(role, RoleDTO.class);
    }
}
