package com.example.gardenedennft.marketplace.controller;

import com.example.gardenedennft.marketplace.entity.request.MarketplaceRequest;
import com.example.gardenedennft.marketplace.service.MarketplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/marketplace")
@RequiredArgsConstructor
public class MarketplaceController {

    private final MarketplaceService marketplaceService;


    @PostMapping("/add-marketplace")
    public ResponseEntity<?> addMarketplace(@RequestBody MarketplaceRequest request){
        return new ResponseEntity<>(marketplaceService.createMarketplace(
                request
        ),HttpStatus.CREATED);
    }
}
