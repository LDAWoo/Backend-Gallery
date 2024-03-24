package com.example.gardenedennft.artwork.entity.response;

import com.example.gardenedennft.artwork.dto.ArtworkDTO;
import com.example.gardenedennft.utils.BaseResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class ArtworkResponse extends BaseResponse<ArtworkDTO> {
}
