package com.example.gardenedennft.TransactionType;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactionType")
public class TransactionTypeController {

    private final TransactionTypeService transactionTypeService;

//    @PreAuthorize("hasAnyAuthority('admin:master')")
    @PostMapping("/add-transactionType")
    public ResponseEntity<?> addTransactionType(@RequestBody TransactionTypeRequest request){
        transactionTypeService.addTransactionType(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getTransactionTypeById/{id}")
    public ResponseEntity<?> getTransactionTypeById(@PathVariable String id){
        UUID uuid = UUID.fromString(id);
        return new ResponseEntity<>(transactionTypeService.getTransactionTypeById(uuid),HttpStatus.OK);
    }

    @GetMapping("/getTransactionTypeByType/{type}")
    public ResponseEntity<?> getTransactionTypeByType(@PathVariable String type){
        return new ResponseEntity<>(transactionTypeService.getTransactionTypeByType(type),HttpStatus.OK);
    }

    @GetMapping("/getAllTransactionType")
     public ResponseEntity<?> getAllTransactionType(){
        return new ResponseEntity<>(transactionTypeService.getAll(),HttpStatus.OK);
    }


}
