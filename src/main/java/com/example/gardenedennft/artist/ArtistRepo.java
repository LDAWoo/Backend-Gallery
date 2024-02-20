package com.example.gardenedennft.artist;

import com.example.gardenedennft.utils.BaseResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class ArtistRepo extends BaseResponse<ArtistDTO> {
}
