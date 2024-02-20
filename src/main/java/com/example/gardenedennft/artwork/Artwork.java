package com.example.gardenedennft.artwork;

import com.example.gardenedennft.attribute.Attribute;
import com.example.gardenedennft.category.Category;
import com.example.gardenedennft.constant.SystemConstant;
import com.example.gardenedennft.favoriteartwork.FavoriteArtwork;
import com.example.gardenedennft.owner.Owner;
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
    private String chain;
    private Double price;
    private Double lastPrice;
    private Integer supply;
    private Integer royalty;
    private Integer minted;
    private Date minted_date;
    private Integer view_count;
    private Integer status;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name ="_category_artwork",
            joinColumns = {@JoinColumn(name = "id_artwork", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")}
    )
    private List<Category> categories = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "_id_artwork")
    private Owner owner;

    @JsonIgnore
    @OneToMany(mappedBy = "artwork", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

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
}
