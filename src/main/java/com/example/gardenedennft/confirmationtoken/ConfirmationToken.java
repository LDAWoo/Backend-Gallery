package com.example.gardenedennft.confirmationtoken;


import com.example.gardenedennft.artist.Artist;
import com.example.gardenedennft.owner.Owner;
import com.example.gardenedennft.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_confirmationToken")
public class ConfirmationToken extends BaseEntity {
    @Column(nullable = false)
    private String token;
    @Column(nullable = true)
    private LocalDateTime createdAt;
    @Column(nullable = true)
    private LocalDateTime expiresAt;
    @Column(nullable = true)
    private LocalDateTime confirmAt;
    @ManyToOne
    @JoinColumn(nullable = false, name = "_id_artist")
    private Artist artist;
}
