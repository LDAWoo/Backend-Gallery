package com.example.gardenedennft.transaction;

import com.example.gardenedennft.TransactionType.TransactionTypeService;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionResponse transactionResponse;

    @Override
    public void createTransaction(Transaction transaction) {
        transactionResponse.save(transaction);
    }

    @Override
    public Transaction findTransactionByArtworkId(Integer id) {
        Transaction transaction = transactionResponse.findTransactionByArtworkId(id)
                .orElseThrow(() -> new ResourceNotFoundException(("not find transaction with id "+id)));
        return transaction;
    }
}
