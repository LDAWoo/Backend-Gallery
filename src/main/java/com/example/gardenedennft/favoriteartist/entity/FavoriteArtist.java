package com.example.gardenedennft.favoriteartist.entity;

import com.example.gardenedennft.artist.Artist;
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
@Table(name = "_favoriteArtist")
public class FavoriteArtist extends BaseEntity {
    private String wallet_address;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "id_favoriteArtist")
    private Artist artist;

}
