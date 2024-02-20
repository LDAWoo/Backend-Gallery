package com.example.gardenedennft.historycreatenft;

import com.example.gardenedennft.constant.SystemConstant;
import com.example.gardenedennft.utils.BaseDTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.Date;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class HistoryCreateNFTDTO extends BaseDTO {
    private String email;
    private String wallet_address;
    private String name;
    private String symbolNFT;
    private String symbolArtist;
    private String description;
    private String image_url;
    private String id_primary_category;
    private String id_secondary_category;
    private String discord_url;
    private String twitter_url;
    private String telegram_url;
    private String website_url;
    private Date mint_date;
    private Double supply;
    private Integer status;
}
