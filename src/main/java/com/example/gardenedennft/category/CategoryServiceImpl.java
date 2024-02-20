package com.example.gardenedennft.category;

import com.example.gardenedennft.exception.ResourceDuplicateException;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryResponse categoryResponse;

    private final CategoryDTOMapper categoryDTOMapper;
    @Override
    public CategoryRepo getAllCategory() {
        CategoryRepo categoryRepo = CategoryRepo.builder()
                .listResult(
                        categoryResponse
                                .findAll()
                                .stream()
                                .map(categoryDTOMapper)
                                .toList())
                                .build();
        return categoryRepo;
    }

    @Override
    public void addCategory(Category category) {
        if(categoryResponse.existsCategoryByName(category.getName()))
            throw new ResourceDuplicateException("Add category fail "+category.getName() + " duplicate!.");

        categoryResponse.save(category);
    }

    @Override
    public Category findCategoryById(UUID id) {
        Category category = categoryResponse.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not found with id "+id));
        return category;
    }

}
