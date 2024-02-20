package com.example.gardenedennft.attribute;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AttributeDTOMapper implements Function<List<Attribute>, List<AttributeDTO>> {

    @Override
    public List<AttributeDTO> apply(List<Attribute> attributes) {

        return attributes
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private AttributeDTO mapToDTO(Attribute attribute) {
        AttributeDTO attributeDTO = AttributeDTO.builder()
                .id(attribute.getId())
                .trait_type(attribute.getTrait_type())
                .value(attribute.getValue())
                .build();
        return attributeDTO;
    }
}
