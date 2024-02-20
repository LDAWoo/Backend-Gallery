package com.example.gardenedennft.favoriteartwork;

import com.example.gardenedennft.artwork.Artwork;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "_favoriteArtwork")
public class FavoriteArtwork extends BaseEntity {
    private String wallet_address;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "_id_favoriteArtwork")
    private Artwork artwork;
}
