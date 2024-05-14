package com.example.gardenedennft.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TransactionDTOMapper implements Function<Transaction , TransactionDTO> {

    @Override
    public TransactionDTO apply(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .signature(transaction.getSignature())
                .amount(transaction.getAmount())
                .royalty_amount(transaction.getRoyalty_amount())
                .buyer_wallet_address(transaction.getBuyer_wallet_address())
                .seller_wallet_address(transaction.getSeller_wallet_address())
                .fee(transaction.getFee())
                .build();
    }
}
