package com.example.gardenedennft.token;


import com.example.gardenedennft.artist.entity.Artist;
import com.example.gardenedennft.utils.BaseID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "_token")
public class Token extends BaseID {
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private Boolean expired;
    private Boolean revoked;
    @ManyToOne
    @JoinColumn(name = "_id_token")
    private Artist artist;
}
