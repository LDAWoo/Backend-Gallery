package com.example.gardenedennft.historycreatenft;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gardeneden")
public class HistoryCreateNFTController {

    private final HistoryCreateNFTService historyCreateNFTService;

    @PostMapping("/creatorHistoryNFT")
    public ResponseEntity<?> creatorHistoryNFT(@RequestBody HistoryCreateNFTRequest request){
        return new ResponseEntity<>(historyCreateNFTService.createHistoryNFT(request), HttpStatus.OK);
    }

    @GetMapping("/findHistoryCreateNFTById/{id}")
    public ResponseEntity<?> findHistoryCreateNFTById(@PathVariable String id){
        UUID uuid = UUID.fromString(id);
        return new ResponseEntity<>(historyCreateNFTService.findHistoryCreateNFTById(uuid), HttpStatus.OK);
    }

    @GetMapping("/findAllHistoryCreateNFTByEmail/{email}")
    public ResponseEntity<?> findAllHistoryCreateNFTByEmail(@PathVariable String email){
        return new ResponseEntity<>(historyCreateNFTService.findHistoryCreateNFTByEmail(email), HttpStatus.OK);
    }

    @PutMapping("/updateHistoryCreateNFT")
    public ResponseEntity<?> updateHistoryCreateNFT(@RequestBody HistoryCreateNFT historyCreateNFT){
        historyCreateNFTService.updateHistoryCreateNFT(historyCreateNFT);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

