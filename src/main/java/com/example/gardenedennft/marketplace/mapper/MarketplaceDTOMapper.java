package com.example.gardenedennft.marketplace.mapper;

import com.example.gardenedennft.marketplace.dto.MarketplaceDTO;
import com.example.gardenedennft.marketplace.entity.Marketplace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MarketplaceDTOMapper implements Function<Marketplace, MarketplaceDTO> {
    @Override
    public MarketplaceDTO apply(Marketplace marketplace) {
        return MarketplaceDTO.builder()
                .marketplaceAddress(marketplace.getMarketplaceAddress())
                .creatorAddress(marketplace.getCreatorAddress())
                .currentAddress(marketplace.getCurrentAddress())
                .marketplaceAuthority(marketplace.getMarketplaceAuthority())
                .build();
    }
}
