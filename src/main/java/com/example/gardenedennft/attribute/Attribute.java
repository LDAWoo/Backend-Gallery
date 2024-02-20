package com.example.gardenedennft.attribute;

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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_attribute")
public class Attribute extends BaseEntity {
    private String trait_type;
    private String value;

    @ManyToOne
    @JoinColumn(name = "_id_artwork")
    private Artwork artwork;
}
