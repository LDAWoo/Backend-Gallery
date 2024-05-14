package com.example.gardenedennft.artwork.entity;

import com.example.gardenedennft.attribute.entity.Attribute;
import com.example.gardenedennft.category.Category;
import com.example.gardenedennft.favoriteartwork.entity.FavoriteArtwork;
import com.example.gardenedennft.marketplace.entity.Marketplace;
import com.example.gardenedennft.owner.entity.Owner;
import com.example.gardenedennft.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder
@Table(name="_artwork")
public class Artwork implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String symbol;
    @Nationalized
    private String description;
    private String image_url;
    private String wallet_address;
    private String tokenAddress;
    private String chain;
    private Double price;
    private Double lastPrice;
    private Integer supply;
    private Integer royalty;
    private Integer minted;
    private Date minted_date;
    private Integer view_count;
    private Integer status;
    private Date listedDate;
    private String listState;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name ="_category_artwork",
            joinColumns = {@JoinColumn(name = "id_artwork", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")}
    )
    private List<Category> categories = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "_id_owner")
    private Owner owner;

    @JsonIgnore
    @OneToMany(mappedBy = "artwork", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "_id_marketplace")
    private Marketplace marketplace;

    @JsonIgnore
    @OneToMany(mappedBy = "artwork", fetch = FetchType.LAZY)
    private List<FavoriteArtwork> favoriteArtworks;

    @JsonIgnore
    @OneToMany(mappedBy = "artwork", fetch = FetchType.LAZY)
    private List<Attribute> attributes;

    @CreatedDate
    private Date createdDate = new Date();
    @CreatedBy String createdBy;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private Date lastModifiedDate = new Date();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artwork artwork = (Artwork) o;
        return Objects.equals(id, artwork.id) && Objects.equals(name, artwork.name) && Objects.equals(symbol, artwork.symbol) && Objects.equals(description, artwork.description) && Objects.equals(image_url, artwork.image_url) && Objects.equals(wallet_address, artwork.wallet_address) && Objects.equals(chain, artwork.chain) && Objects.equals(price, artwork.price) && Objects.equals(lastPrice, artwork.lastPrice) && Objects.equals(supply, artwork.supply) && Objects.equals(royalty, artwork.royalty) && Objects.equals(minted, artwork.minted) && Objects.equals(minted_date, artwork.minted_date) && Objects.equals(view_count, artwork.view_count) && Objects.equals(status, artwork.status) && Objects.equals(categories, artwork.categories) && Objects.equals(owner, artwork.owner) && Objects.equals(transactions, artwork.transactions) && Objects.equals(favoriteArtworks, artwork.favoriteArtworks) && Objects.equals(attributes, artwork.attributes) && Objects.equals(createdDate, artwork.createdDate) && Objects.equals(createdBy, artwork.createdBy) && Objects.equals(lastModifiedBy, artwork.lastModifiedBy) && Objects.equals(lastModifiedDate, artwork.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, symbol, description, image_url, wallet_address, chain, price, lastPrice, supply, royalty, minted, minted_date, view_count, status, categories, owner, transactions, favoriteArtworks, attributes, createdDate, createdBy, lastModifiedBy, lastModifiedDate);
    }
}
