package com.example.gardenedennft.marketplace.entity;

import com.example.gardenedennft.artwork.entity.Artwork;
import com.example.gardenedennft.transaction.Transaction;
import com.example.gardenedennft.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_marketplace")
public class Marketplace extends BaseEntity {
    private String marketplaceAddress;
    private String marketplaceAuthority;
    private String creatorAddress;
    private String currentAddress;
    private Boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "marketplace", fetch = FetchType.LAZY)
    private List<Artwork> artworks;

    @JsonIgnore
    @OneToMany(mappedBy = "marketplace", fetch = FetchType.LAZY)
    private List<Transaction> transactions;
}
