package com.example.gardenedennft.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryResponse extends JpaRepository<Category, UUID> {
    boolean existsCategoryByName(String name);
}
