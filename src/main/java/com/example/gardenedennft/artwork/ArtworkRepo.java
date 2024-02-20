package com.example.gardenedennft.artwork;

import com.example.gardenedennft.utils.BaseResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class ArtworkRepo extends BaseResponse<ArtworkDTO> {
}
