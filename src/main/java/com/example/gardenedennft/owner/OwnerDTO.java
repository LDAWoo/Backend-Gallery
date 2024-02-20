package com.example.gardenedennft.owner;


import com.example.gardenedennft.utils.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerDTO{
    private UUID id;
    private String walletAddress;
    private String email;
    private String name;
    private String symbol;
    private String image_url;
    private String discord_url;
    private String twitter_url;
    private String telegram_url;
    private String website_url;
    private String bio;
    private Boolean ownerStatus;
    private Boolean artistStatus;
}
