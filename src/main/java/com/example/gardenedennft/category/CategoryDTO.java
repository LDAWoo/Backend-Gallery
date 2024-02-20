package com.example.gardenedennft.category;

import com.example.gardenedennft.utils.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO extends BaseDTO {
    private String name;
}
