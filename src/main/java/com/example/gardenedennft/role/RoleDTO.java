package com.example.gardenedennft.role;

import com.example.gardenedennft.utils.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class RoleDTO extends BaseDTO {

    private String name;

    private String code;
}
