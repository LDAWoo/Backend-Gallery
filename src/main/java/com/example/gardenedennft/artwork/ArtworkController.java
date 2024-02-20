package com.example.gardenedennft.artwork;

import com.example.gardenedennft.constant.SystemConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


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

    @GetMapping("/getArtworkReviewedByEmail")
    public ResponseEntity<?> getArtworkReviewedByEmail(@RequestParam String email){
        return new ResponseEntity<>(artworkService.findArtworkByEmail(email),HttpStatus.OK);
    }

    @GetMapping("/getArtworkByIdAndEmail")
    public ResponseEntity<?> getArtworkByIdAndEmail(@RequestParam Integer id, @RequestParam String email){
        return new ResponseEntity<>(artworkService.findArtworkByIdAndEmail(id,email),HttpStatus.OK);
    }

    @GetMapping("/getArtworkByIdOwner/{id}")
    public ResponseEntity<?> findArtworkByIdOwner(@PathVariable String id){
        UUID uuid = UUID.fromString(id);
        return new ResponseEntity<>(artworkService.findArtworkByIdOwnerAndStatus(uuid, SystemConstant.STATUS_NFT_ACTIVE),HttpStatus.OK);
    }

    @PutMapping("/updateArtwork")
    public ResponseEntity<?> updateArtwork(@RequestBody Artwork artwork){
        artworkService.updateNFT(artwork);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
