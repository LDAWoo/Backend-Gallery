package com.example.gardenedennft.favoriteowner.controller;

import com.example.gardenedennft.favoriteowner.dto.request.FavoriteOwnerRequest;
import com.example.gardenedennft.favoriteowner.service.FavoriteOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/favoriteOwner")
@RequiredArgsConstructor
public class FavoriteOwnerController {

    private final FavoriteOwnerService favoriteOwnerService;

    @PostMapping("/add-favoriteOwner")
    public ResponseEntity<?> addFavoriteOwner(@RequestBody FavoriteOwnerRequest favoriteOwnerRequest){
        favoriteOwnerService.addFavoriteOwner(favoriteOwnerRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
