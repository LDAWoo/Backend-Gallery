package com.example.gardenedennft.favoriteartist.controller;

import com.example.gardenedennft.favoriteartist.dto.request.FavoriteArtistRequest;
import com.example.gardenedennft.favoriteartist.service.FavoriteArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/favoriteArtist")
@RequiredArgsConstructor
public class FavoriteArtistController {

    private final FavoriteArtistService favoriteArtistService;

    @PostMapping("/add-favoriteArtist")
    public ResponseEntity<?> addFavoriteOwner(@RequestBody FavoriteArtistRequest favoriteArtistRequest){
        return new ResponseEntity<>(favoriteArtistService.addFavoriteArtist(favoriteArtistRequest),HttpStatus.OK);
    }

}
