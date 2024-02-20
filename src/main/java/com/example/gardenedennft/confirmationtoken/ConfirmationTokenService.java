package com.example.gardenedennft.confirmationtoken;

public interface ConfirmationTokenService {
    ConfirmationTokenDTO findByToken(String token);
    void setConfirmAt(String token);
    void saveConfirmationToken(ConfirmationToken token);
}
