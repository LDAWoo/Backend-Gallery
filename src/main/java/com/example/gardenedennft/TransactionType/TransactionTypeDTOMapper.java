package com.example.gardenedennft.TransactionType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TransactionTypeDTOMapper implements Function<TransactionType, TransactionTypeDTO> {
    @Override
    public TransactionTypeDTO apply(TransactionType transactionType) {
        TransactionTypeDTO transactionTypeDTO = TransactionTypeDTO.builder()
                .id(transactionType.getId())
                .type(transactionType.getType())
                .createdBy(transactionType.getCreateBy())
                .createdDate(transactionType.getCreatedDate())
                .lastModifiedBy(transactionType.getLastModifiedBy())
                .lastModifiedDate(transactionType.getLastModifiedDate())
                .build();
        return transactionTypeDTO;
    }
}
