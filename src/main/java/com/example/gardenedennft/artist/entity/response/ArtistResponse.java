package com.example.gardenedennft.artist.entity.response;

import com.example.gardenedennft.artist.dto.ArtistDTO;
import com.example.gardenedennft.utils.BaseResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class ArtistResponse extends BaseResponse<ArtistDTO> {
}
