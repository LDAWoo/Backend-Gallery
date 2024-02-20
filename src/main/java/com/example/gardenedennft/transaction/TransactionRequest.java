package com.example.gardenedennft.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private UUID _id_transactionType;
    private UUID _id_Artwork;
    private String signature;
    private Double amount;
    private String buyer_wallet_address;
    private String seller_wallet_address;
    private Double fee;
    private Integer royalty_amount;
}
