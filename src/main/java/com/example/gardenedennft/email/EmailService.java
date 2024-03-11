package com.example.gardenedennft.email;

public interface EmailService {
    void send(String to, String email, String subject);

    String buildEmail(String email, String link, boolean isValidEmail);

    String buildEmailVerify(String email, String link, boolean isValidEmail);
}
