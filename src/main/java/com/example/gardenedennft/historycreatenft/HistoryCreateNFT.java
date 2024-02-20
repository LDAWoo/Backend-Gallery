package com.example.gardenedennft.historycreatenft;


import com.example.gardenedennft.constant.SystemConstant;
import com.example.gardenedennft.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_historyCreate_NFT")
public class HistoryCreateNFT extends BaseEntity {
    private String email;
    private String wallet_address;
    @Column(nullable = true, columnDefinition = "TEXT DEFAULT ''")
    private String name;
    @Column(nullable = true, columnDefinition = "TEXT DEFAULT ''")
    private String symbolNFT;
    @Column(nullable = true, columnDefinition = "TEXT DEFAULT ''")
    private String symbolArtist;
    @Column(nullable = true, columnDefinition = "TEXT DEFAULT ''")
    private String description;
    @Column(nullable = true, columnDefinition = "TEXT DEFAULT ''")
    private String image_url;
    @Column(nullable = true, columnDefinition = "TEXT DEFAULT ''")
    private String id_primary_category;
    @Column(nullable = true, columnDefinition = "TEXT DEFAULT ''")
    private String id_secondary_category;
    @Column(nullable = true, columnDefinition = "TEXT DEFAULT ''")
    private String discord_url;
    @Column(nullable = true, columnDefinition = "TEXT DEFAULT ''")
    private String twitter_url;
    @Column(nullable = true, columnDefinition = "TEXT DEFAULT ''")
    private String telegram_url;
    @Column(nullable = true, columnDefinition = "TEXT DEFAULT ''")
    private String website_url;
    private Date mint_date;
    private Double supply;
    private Integer status= SystemConstant.STATUS_HISTORY_CREATE_NFT_NO_ACTIVE;
}
