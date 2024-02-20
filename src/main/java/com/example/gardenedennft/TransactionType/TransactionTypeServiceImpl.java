package com.example.gardenedennft.TransactionType;

import com.example.gardenedennft.exception.ResourceDuplicateException;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService{

    private final TransactionTypeResponse transactionTypeResponse;

    private final TransactionTypeDTOMapper transactionTypeDTOMapper;

    @Override
    public void addTransactionType(TransactionTypeRequest request) {
        if(existsTransactionTypeByType(request.getType()))
            throw new ResourceDuplicateException("TransactionType exits by "+ request.getType()+ " duplicate!.");

        TransactionType transactionType = new TransactionType();
        transactionType.setType(request.getType());
        transactionTypeResponse.save(transactionType);
    }

    @Override
    public Boolean existsTransactionTypeByType(String type) {
        return transactionTypeResponse.existsTransactionTypeByType(type);
    }

    @Override
    public TransactionType getTransactionTypeByType(String type) {
        TransactionType transactionType = transactionTypeResponse.findTransactionTypeByType(type)
                .orElseThrow(() -> new ResourceDuplicateException("TransactionType not exits by "+ type+ " duplicate!."));
        return transactionType;
    }

    @Override
    public TransactionType getTransactionTypeById(UUID id) {
        TransactionType transactionType = transactionTypeResponse.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(("Transaction Type not found id by"+id)));

        return transactionType;
    }

    @Override
    public TransactionRepo getAll() {
        TransactionRepo transactionRepo = TransactionRepo.builder()
                .listResult(
                        transactionTypeResponse
                                .findAll()
                                .stream()
                                .map(transactionTypeDTOMapper)
                                .toList()
                                ).build();
        return transactionRepo;
    }
}
