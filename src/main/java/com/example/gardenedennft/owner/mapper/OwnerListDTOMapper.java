package com.example.gardenedennft.owner.mapper;

import com.example.gardenedennft.owner.entity.Owner;
import com.example.gardenedennft.owner.dto.OwnerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerListDTOMapper implements Function<List<Owner>, List<OwnerDTO>> {

    private final OwnerDTOMapper ownerDTOMapper;

    @Override
    public List<OwnerDTO> apply(List<Owner> owners) {
        return owners.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private OwnerDTO mapToDTO(Owner owner) {
        return ownerDTOMapper.apply(owner);
    }
}
