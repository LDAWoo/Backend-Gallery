package com.example.gardenedennft.category;

import com.example.gardenedennft.utils.BaseResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder(toBuilder = true)
public class CategoryRepo extends BaseResponse<CategoryDTO> {
}
