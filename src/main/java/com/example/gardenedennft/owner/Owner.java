package com.example.gardenedennft.owner;

import com.example.gardenedennft.artist.Artist;
import com.example.gardenedennft.artwork.Artwork;
import com.example.gardenedennft.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "_owner")
public class Owner extends BaseEntity {
    private String wallet_address;
    private Boolean status;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Artwork> artworks;

    @ManyToOne
    @JoinColumn(name = "id_artist")
    public Artist artist;

}
