package com.example.gardenedennft.attribute.controller;


import com.example.gardenedennft.attribute.service.AttributeService;
import com.example.gardenedennft.attribute.entity.request.AttributeArtworkRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/attribute")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @PostMapping("/add-attribute")
    public ResponseEntity<?> addAddAttribute(@RequestBody AttributeArtworkRequest attributeArtworkRequest) {
        attributeService.addAttribute(
                attributeArtworkRequest.getAttributeRequest(),
                attributeArtworkRequest.getAttributes()
        );
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getAttributeByIdArtwork/{id}")
    public ResponseEntity<?> getAttributeByIdArtwork(@PathVariable Integer id){
        return new ResponseEntity<>(attributeService.findAttributeByIdArtwork(id),HttpStatus.OK);
    }

    @DeleteMapping("/deleteAttributeById/{id}")
    public ResponseEntity<?> deleteAttributeById(@PathVariable String id){
        UUID uuid = UUID.fromString(id);
        attributeService.deleteAttributeById(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
