package com.example.gardenedennft.historycreatenft;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class HistoryCreateNFTDTOMapper implements Function<HistoryCreateNFT, HistoryCreateNFTDTO> {

    @Override
    public HistoryCreateNFTDTO apply(HistoryCreateNFT historyCreateNFT) {
        HistoryCreateNFTDTO historyCreateNFTDTO = HistoryCreateNFTDTO
                .builder()
                .id(historyCreateNFT.getId())
                .email(historyCreateNFT.getEmail())
                .name(historyCreateNFT.getName())
                .symbolNFT(historyCreateNFT.getSymbolNFT())
                .symbolArtist(historyCreateNFT.getSymbolArtist())
                .description(historyCreateNFT.getDescription())
                .image_url(historyCreateNFT.getImage_url())
                .discord_url(historyCreateNFT.getDiscord_url())
                .twitter_url(historyCreateNFT.getTwitter_url())
                .telegram_url(historyCreateNFT.getTelegram_url())
                .wallet_address(historyCreateNFT.getWallet_address())
                .website_url(historyCreateNFT.getWebsite_url())
                .status(historyCreateNFT.getStatus())
                .mint_date(historyCreateNFT.getMint_date())
                .supply(historyCreateNFT.getSupply())
                .id_primary_category(historyCreateNFT.getId_primary_category())
                .id_secondary_category(historyCreateNFT.getId_secondary_category())
                .createdBy(historyCreateNFT.getCreateBy())
                .createdDate(historyCreateNFT.getCreatedDate())
                .lastModifiedBy(historyCreateNFT.getLastModifiedBy())
                .lastModifiedDate(historyCreateNFT.getLastModifiedDate())
                .build();
        return historyCreateNFTDTO;
    }
}
