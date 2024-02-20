package com.example.gardenedennft.favoriteartwork.dto.response;


import com.example.gardenedennft.favoriteartwork.dto.FavoriteArtWorkDTO;
import com.example.gardenedennft.utils.BaseResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class FavoriteArtWorkResponse extends BaseResponse<FavoriteArtWorkDTO> {
}
