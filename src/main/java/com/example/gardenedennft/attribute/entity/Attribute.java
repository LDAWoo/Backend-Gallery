package com.example.gardenedennft.attribute.entity;

import com.example.gardenedennft.artwork.entity.Artwork;
import com.example.gardenedennft.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Attribute attribute = (Attribute) o;
        return Objects.equals(trait_type, attribute.trait_type) && Objects.equals(value, attribute.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), trait_type, value, artwork);
    }
}
