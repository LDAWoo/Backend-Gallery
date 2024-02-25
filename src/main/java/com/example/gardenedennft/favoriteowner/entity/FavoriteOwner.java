package com.example.gardenedennft.favoriteowner.entity;

import com.example.gardenedennft.owner.Owner;
import com.example.gardenedennft.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_favoriteOwner")
public class FavoriteOwner extends BaseEntity {
    private String wallet_address;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "id_favoriteOwner")
    private Owner owner;

}
