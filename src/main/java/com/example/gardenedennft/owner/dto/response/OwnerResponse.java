package com.example.gardenedennft.owner.dto.response;

import com.example.gardenedennft.owner.dto.OwnerDTO;
import com.example.gardenedennft.utils.BaseResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class OwnerResponse extends BaseResponse<OwnerDTO> {
}
