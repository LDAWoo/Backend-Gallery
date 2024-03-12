package com.example.gardenedennft.artist;

import java.util.UUID;

public interface ArtistSqlNativeResult {
    UUID getId();

    String getName();

    String getSymbol();

    String getEmail();

    String getImage_url();

    String getDiscord_url();

    String getTelegram_url();

    String getTwitter_url();

    String getBio();

}
