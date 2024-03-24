package com.example.gardenedennft.artwork.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkRequest {
    private UUID id_history_create_nft;
    private UUID id_owner;
    private String wallet_address;
    private String name;
    private String symbolNFT;
    private String description;
    private String image_url;
    private Integer supply;
    private Integer royalty;
    private Integer minted = 0;
    private Double price;
    private Double lastPrice;
    private String chain;
    private Date mint_date;
    private Integer view_count;
    private Integer status;
}
