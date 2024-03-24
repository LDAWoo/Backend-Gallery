package com.example.gardenedennft.artwork.controller;

import com.example.gardenedennft.artwork.entity.request.ArtworkArtistRequest;
import com.example.gardenedennft.artwork.service.ArtworkService;
import com.example.gardenedennft.artwork.entity.request.ArtworkTransactionRequest;
import com.example.gardenedennft.artwork.entity.Artwork;
import com.example.gardenedennft.artwork.entity.request.ArtworkConditionRequest;
import com.example.gardenedennft.attribute.entity.Attribute;
import com.example.gardenedennft.constant.SystemConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@RestController
@RequestMapping("/api/v1/artwork")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService artworkService;

    @PostMapping("/add-artwork")
    public ResponseEntity<?> addArtWork(@RequestBody ArtworkTransactionRequest artworkTransactionRequest){
        artworkService.createNFT(
                artworkTransactionRequest.getArtistRequest(),
                artworkTransactionRequest.getArtworkRequest(),
                artworkTransactionRequest.getTransactionRequest(),
                artworkTransactionRequest.getCategoryIds());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getArtworkByWalletAddress/{walletAddress}")
    public ResponseEntity<?> getArtworkByWalletAddress (@PathVariable String walletAddress){
        return new ResponseEntity<>(artworkService.findArtworkByWalletAddress(walletAddress),HttpStatus.OK);
    }

    @GetMapping("/getArtworkByWalletAddressAndByCondition/{walletAddress}")
    public ResponseEntity<?> getArtworkByWalletAddressAndByCondition (
            @PathVariable String walletAddress,
            @RequestParam("symbol") String symbol
    ){
        return new ResponseEntity<>(artworkService.findArtworkByWalletAddressAndByCondition(
                new ArtworkArtistRequest(
                        symbol,
                        walletAddress
                )
        ),HttpStatus.OK);
    }

    @GetMapping("/getArtworkReviewedByEmail")
    public ResponseEntity<?> getArtworkReviewedByEmail(@RequestParam String email){
        return new ResponseEntity<>(artworkService.findArtworkByEmail(email),HttpStatus.OK);
    }

    @GetMapping("/getArtworkByIdAndEmail")
    public ResponseEntity<?> getArtworkByIdAndEmail(@RequestParam Integer id, @RequestParam String email){
        return new ResponseEntity<>(artworkService.findArtworkByIdAndEmail(id,email),HttpStatus.OK);
    }

    @GetMapping("/getArtworkByIdArtist/{id}")
    public ResponseEntity<?> findArtworkByIdArtist(@PathVariable String id){
        UUID uuid = UUID.fromString(id);
        return new ResponseEntity<>(artworkService.findArtworkByIdArtistAndStatus(uuid, SystemConstant.STATUS_NFT_ACTIVE),HttpStatus.OK);
    }

    @GetMapping("/getArtworkByIdArtistAndCondition")
    public ResponseEntity<?> findArtworkByIdArtistAndCondition(@RequestParam("id") String id, @RequestParam("attributes") String attributes, @RequestParam("name") String name) throws JsonProcessingException {
        String nameNFT = name.trim();
        String decodedAttributes = URLDecoder.decode(attributes, StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(decodedAttributes);

        List<Attribute> attributeList = new ArrayList<>();

        rootNode.fields().forEachRemaining(entry -> {
            String attributeName = entry.getKey();
            JsonNode arrayNode = entry.getValue();
            for (JsonNode node : arrayNode) {
                JsonNode valueNode = node.get("value");
                if (valueNode != null && valueNode.isTextual()) {
                    Attribute attribute = new Attribute();
                    attribute.setTrait_type(attributeName);
                    attribute.setValue(valueNode.asText());
                    attributeList.add(attribute);
                }
            }
        });

        return new ResponseEntity<>
                (
                artworkService.findArtworkByIdArtistAndStatusAndCondition(
                new ArtworkConditionRequest(
                        UUID.fromString(id),
                        SystemConstant.STATUS_NFT_ACTIVE,
                        nameNFT,
                        attributeList)),
                        HttpStatus.OK);
    }

    @PutMapping("/updateArtwork")
    public ResponseEntity<?> updateArtworkById(@RequestBody Artwork artwork){
        artworkService.updateArtworkById(artwork);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
