package com.example.gardenedennft.marketplace.dto;

import com.example.gardenedennft.utils.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketplaceDTO {
    private String marketplaceAddress;
    private String marketplaceAuthority;
    private String creatorAddress;
    private String currentAddress;
}
