package com.example.gardenedennft.category;


import java.util.UUID;

public interface CategoryService {

    CategoryRepo getAllCategory();

    void addCategory(Category category);

    Category findCategoryById(UUID id);

}
