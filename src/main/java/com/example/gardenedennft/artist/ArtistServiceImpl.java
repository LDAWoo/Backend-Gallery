package com.example.gardenedennft.artist;

import com.example.gardenedennft.confirmationtoken.ConfirmationToken;
import com.example.gardenedennft.confirmationtoken.ConfirmationTokenDTO;
import com.example.gardenedennft.confirmationtoken.ConfirmationTokenService;
import com.example.gardenedennft.email.EmailService;
import com.example.gardenedennft.exception.ApiRequestException;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import com.example.gardenedennft.jwt.JwtService;
import com.example.gardenedennft.owner.Owner;
import com.example.gardenedennft.owner.OwnerDTO;
import com.example.gardenedennft.owner.OwnerResponse;
import com.example.gardenedennft.owner.OwnerService;
import com.example.gardenedennft.token.Token;
import com.example.gardenedennft.token.TokenResponse;
import com.example.gardenedennft.token.TokenType;
import com.example.gardenedennft.utils.BaseUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService{

    private final ArtistResponse artistResponse;

    private final ConfirmationTokenService confirmationTokenService;

    private final BaseUrlService baseUrlService;

    private final EmailService emailService;

    private final JwtService jwtService;

    private final ArtistDTOMapper artistDTOMapper;

    private final TokenResponse tokenResponse;

    private final ArtistOwnerDTOMapper artistOwnerDTOMapper;

    private final OwnerService ownerService;
    @Override
    public Artist findArtistByEmail(String email) {
        Artist artist = artistResponse.findArtistByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with email: "+email));
        return artist;
    }

    @Override
    public void authentication(ArtistAuthenticationRequest request) {
        String email = request.getEmail();
        if(!checkEmail(email)) {
            Artist artist = Artist.builder()
                    .email(email)
                    .build();
            artistResponse.save(artist);
        }

        String tokenConfirm = String.valueOf(UUID.randomUUID());

        Artist artist = findArtistByEmail(email);

        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(tokenConfirm)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .artist(artist)
                .build();

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = baseUrlService.getBaseUrl() + "/api/v1/gardeneden/auth/confirm?token="+tokenConfirm;

        emailService.send(
                email,
                emailService.buildEmail(email,link,false),
                "Creators"
        );
    }

    @Override
    public Boolean existsArtistByEmail(String email) {
        return artistResponse.existsArtistByEmail(email);
    }

    @Override
    public void updateStatusByEmail(String email) {
        artistResponse.updateStatusByEmail(email);
    }

    @Override
    public ArtistDTO confirmationToken(String token) {
        ConfirmationTokenDTO confirmationToken = confirmationTokenService.findByToken(token);
        if(confirmationToken.getConfirmAt() !=null){
            throw new ApiRequestException("Artist already confirm!");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now()))
            throw new ApiRequestException("Token expiredAt!");

        confirmationTokenService.setConfirmAt(token);
        updateStatusByEmail(confirmationToken.getArtist().getEmail());

        Artist artist = artistResponse.findOwnerByEmailAndStatus(confirmationToken.getArtist().getEmail(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Find artist by " + confirmationToken.getArtist().getEmail() + "not found"));

        ArtistDTO artistDTO = artistDTOMapper.apply(artist);
        var jwtToken = jwtService.generateToken(artist);
        artistDTO.setToken(jwtToken);

        revokeAllOwnerToken(artist);
        saveOwnerToken(artist, jwtToken);

        return artistDTO;
    }

    @Override
    public Artist findArtistById(UUID id) {
        return artistResponse.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not  with id "+id));
    }

    @Override
    public void updateArtist(Artist artist) {
        artistResponse.findArtistByEmail(artist.getEmail()).ifPresent(existingArtist -> {
            Field[] fields = Artist.class.getDeclaredFields();

            for (Field field: fields) {
                try{
                    field.setAccessible(true);
                    Object newValue = field.get(artist);
                    if(newValue != null){
                        field.set(existingArtist, newValue);
                    }
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
            existingArtist.setLastModifiedDate(new Date());
            artistResponse.save(existingArtist);
        });
    }

    @Override
    public ArtistRepo findAll() {
        return ArtistRepo.builder()
                .listResult(
                        artistResponse.findAll()
                                .stream()
                                .map(artistDTOMapper)
                                .toList()
                ).build();
    }

    @Override
    public ArtistDTO findArtistByToken(String token) {
        Token currentToken = tokenResponse
                .findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with token " + token));

        Optional<Artist> artistOptional = artistResponse.findById(currentToken.getArtist().getId());


        if (artistOptional.isPresent()) {
            ArtistDTO artistDTO = artistDTOMapper.apply(artistOptional.get());
            artistDTO.setToken(token);
            return artistDTO;
        } else {
            throw new ResourceNotFoundException("Artist not found with token " + token);
        }
    }

    @Override
    public ArtistOwnerDTO findArtistBySymbol(String symbol) {
        Artist artist = artistResponse.findArtistBySymbol(symbol)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with symbol: " + symbol));

        ArtistOwnerDTO ownerDTO = artistOwnerDTOMapper.apply(artist);

        List<OwnerDTO> ownerDTOList = ownerService.findAllOwnerByIdArtwork(artist.getId());

        ownerDTO.setOwners(ownerDTOList);

        return ownerDTO;
    }

    private void revokeAllOwnerToken(Artist artist){
        var validArtistToken = tokenResponse.findAllValidTokenByArtist(artist.getId());
        if(validArtistToken.isEmpty()){
            return;
        }

        validArtistToken.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenResponse.saveAll(validArtistToken);
    }

    private void saveOwnerToken(Artist artist, String jwtToken){
        var token = Token.builder()
                .artist(artist)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenResponse.save(token);
    }


    private Boolean checkEmail (String email) {
        return existsArtistByEmail(email);
    }

}
