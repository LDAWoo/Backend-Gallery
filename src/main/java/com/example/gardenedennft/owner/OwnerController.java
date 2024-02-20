package com.example.gardenedennft.owner;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/getOwnerByWalletAddress/{walletAddress}")
    public ResponseEntity<?> getOwnerByWalletAddress(@PathVariable String walletAddress){
        return new ResponseEntity<>(ownerService.findOwnerByWalletAddress(walletAddress),HttpStatus.OK);
    }

    @GetMapping("/getOwnerByIdArtist/{id}")
    public ResponseEntity<?> getOwnerByIdArtist(@PathVariable String id){
        UUID uuid = UUID.fromString(id);
        return new ResponseEntity<>(ownerService.findOwnerByIdArtwork(uuid),HttpStatus.OK);
    }
}
