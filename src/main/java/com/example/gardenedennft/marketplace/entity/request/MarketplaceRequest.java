package com.example.gardenedennft.marketplace.entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketplaceRequest {
    private String marketplaceAddress;
    private String marketplaceAuthority;
    private String creatorAddress;
    private String currentAddress;
    private String signature;
}
