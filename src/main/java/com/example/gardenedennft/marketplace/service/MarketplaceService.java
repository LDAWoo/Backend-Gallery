package com.example.gardenedennft.marketplace.service;

import com.example.gardenedennft.marketplace.entity.Marketplace;
import com.example.gardenedennft.marketplace.entity.request.MarketplaceRequest;

public interface MarketplaceService {
    Marketplace createMarketplace(MarketplaceRequest request);
    Marketplace findMarketplaceByCreatorAddress(String creatorAddress);
}
