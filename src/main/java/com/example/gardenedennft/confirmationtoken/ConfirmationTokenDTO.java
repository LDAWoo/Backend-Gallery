package com.example.gardenedennft.confirmationtoken;

import com.example.gardenedennft.artist.entity.Artist;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class ConfirmationTokenDTO {
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmAt;
    private Artist artist;
}
