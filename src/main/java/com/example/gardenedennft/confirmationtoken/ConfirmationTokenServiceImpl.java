package com.example.gardenedennft.confirmationtoken;


import com.example.gardenedennft.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{

    private final ConfirmationTokenResponse confirmationTokenResponse;

    private final ConfirmationTokenDTOMapper confirmationTokenDTOMapper;

    @Override
    public ConfirmationTokenDTO findByToken(String token) {
        return confirmationTokenResponse.findConfirmationTokenByToken(token)
                .map(confirmationTokenDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(("Find confirmation by token " + token + " not found!")));
    }

    @Override
    public void setConfirmAt(String token) {
        confirmationTokenResponse.updateConfirmAtByToken(token, LocalDateTime.now());
    }

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenResponse.save(token);
    }
}
