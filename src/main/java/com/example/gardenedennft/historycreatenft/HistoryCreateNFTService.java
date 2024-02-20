package com.example.gardenedennft.historycreatenft;

import java.util.Optional;
import java.util.UUID;

public interface HistoryCreateNFTService {

    UUID createHistoryNFT(HistoryCreateNFTRequest request);

    void updateHistoryCreateNFT(HistoryCreateNFT historyCreateNFT);

    HistoryCreateNFT findHistoryCreateNFTById(UUID id);

    HistoryCreateNFT findStatusHistoryCreateNFTById(UUID id,Integer status);

    HistoryCreateNFTRepo findHistoryCreateNFTByEmail(String email);

    void updateStatusHistoryCreateNFTById(UUID id);
}
