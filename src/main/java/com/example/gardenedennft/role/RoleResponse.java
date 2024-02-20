package com.example.gardenedennft.role;

import com.example.gardenedennft.utils.BaseResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class RoleResponse extends BaseResponse<RoleDTO> {
}
