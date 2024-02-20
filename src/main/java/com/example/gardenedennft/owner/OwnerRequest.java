package com.example.gardenedennft.owner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerRequest {
    private UUID _id_artist;
    private String wallet_address;
    private Boolean status;
}
