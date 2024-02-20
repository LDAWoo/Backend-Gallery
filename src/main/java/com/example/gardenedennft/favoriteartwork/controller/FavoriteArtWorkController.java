package com.example.gardenedennft.favoriteartwork.controller;

import com.example.gardenedennft.favoriteartwork.dto.request.FavoriteArtworkRequest;
import com.example.gardenedennft.favoriteartwork.entity.FavoriteArtwork;
import com.example.gardenedennft.favoriteartwork.service.FavoriteArtWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favoriteArtwork")
public class FavoriteArtWorkController {

    private final FavoriteArtWorkService favoriteArtWorkService;

    @PostMapping("/add-favoriteArtwork")
    public ResponseEntity<?> addFavoriteArtWork(@RequestBody FavoriteArtworkRequest favoriteArtworkRequest){
        favoriteArtWorkService.addFavoriteArtWork(favoriteArtworkRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
