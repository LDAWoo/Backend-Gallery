package com.example.gardenedennft.category;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CategoryDTOMapper implements Function<Category, CategoryDTO> {
    @Override
    public CategoryDTO apply(Category category) {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .createdBy(category.getCreateBy())
                .createdDate(category.getCreatedDate())
                .lastModifiedBy(category.getLastModifiedBy())
                .lastModifiedDate(category.getLastModifiedDate())
                .build();
        return categoryDTO;
    }
}
