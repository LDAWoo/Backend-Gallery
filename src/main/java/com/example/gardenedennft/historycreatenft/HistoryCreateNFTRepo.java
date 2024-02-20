package com.example.gardenedennft.historycreatenft;

import com.example.gardenedennft.utils.BaseResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class HistoryCreateNFTRepo extends BaseResponse<HistoryCreateNFTDTO> {
}
