package com.example.gardenedennft.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO{
    private Integer id;
    private String signature;
    private Double amount;
    private String buyer_wallet_address;
    private String seller_wallet_address;
    private Double fee;
    private Double royalty_amount;
}
