package com.example.gardenedennft.marketplace.service.Impl;

import com.example.gardenedennft.TransactionType.TransactionType;
import com.example.gardenedennft.TransactionType.TransactionTypeService;
import com.example.gardenedennft.exception.ResourceDuplicateException;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import com.example.gardenedennft.marketplace.entity.Marketplace;
import com.example.gardenedennft.marketplace.entity.request.MarketplaceRequest;
import com.example.gardenedennft.marketplace.repo.MarketplaceRepo;
import com.example.gardenedennft.marketplace.service.MarketplaceService;
import com.example.gardenedennft.transaction.Transaction;
import com.example.gardenedennft.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketplaceServiceImpl implements MarketplaceService{

    private final MarketplaceRepo marketplaceRepo;

    private final TransactionTypeService transactionTypeService;

    private final TransactionService transactionService;

    @Override
    public Marketplace createMarketplace(MarketplaceRequest request) {
        String createAddress = request.getCreatorAddress();
        String currentAddress = request.getCurrentAddress();
        String marketplaceAddress = request.getMarketplaceAddress();
        String marketplaceAuthority = request.getMarketplaceAuthority();
        String signature = request.getSignature();

//        if(exitsMarketplace(createAddress)){
//           throw new ResourceDuplicateException("Marketplace exits with create address "+createAddress);
//        }

        Marketplace marketplace = Marketplace.builder()
                .creatorAddress(createAddress)
                .currentAddress(currentAddress)
                .marketplaceAddress(marketplaceAddress)
                .marketplaceAuthority(marketplaceAuthority)
                .status(true)
                .build();

        Marketplace marketplaceSave = marketplaceRepo.save(marketplace);

        TransactionType creatorTransactionType = transactionTypeService.getTransactionTypeByType("Creator");

        Transaction transaction = Transaction.builder()
                .transactionType(creatorTransactionType)
                .signature(signature)
                .marketplace(marketplaceSave)
                .build();

        transactionService.createTransaction(transaction);

        return marketplaceSave;
    }

    @Override
    public Marketplace findMarketplaceByCreatorAddress(String creatorAddress) {
        return marketplaceRepo.findMarketplaceByCreatorAddress(creatorAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Not found find marketplace with creator address "+creatorAddress));
    }

    public boolean exitsMarketplace(String creatorAddress){
        return marketplaceRepo.existsMarketplaceByCreateAddress(creatorAddress);
    }
}
