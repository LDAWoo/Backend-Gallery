package com.example.gardenedennft.transaction;

public interface TransactionService {
    void createTransaction(Transaction transaction);
    Transaction findTransactionByArtworkId(Integer id);
}
