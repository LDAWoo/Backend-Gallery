package com.example.gardenedennft.role;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class RoleRequest {
    private String name;
    private String code;
}
