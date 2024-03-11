package com.example.gardenedennft.artist;

import com.example.gardenedennft.artist.entity.request.ArtistRequest;
import com.example.gardenedennft.artwork.ArtworkDTO;
import com.example.gardenedennft.artwork.ArtworkListDTOMapper;
import com.example.gardenedennft.artwork.ArtworkResponse;
import com.example.gardenedennft.artwork.ArtworkService;
import com.example.gardenedennft.confirmationtoken.ConfirmationToken;
import com.example.gardenedennft.confirmationtoken.ConfirmationTokenDTO;
import com.example.gardenedennft.confirmationtoken.ConfirmationTokenService;
import com.example.gardenedennft.constant.SystemConstant;
import com.example.gardenedennft.email.EmailService;
import com.example.gardenedennft.exception.ApiRequestException;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import com.example.gardenedennft.favoriteartwork.dto.FavoriteArtWorkDTO;
import com.example.gardenedennft.favoriteartwork.dto.response.FavoriteArtWorkResponse;
import com.example.gardenedennft.favoriteartwork.mapper.FavoriteListArtworkDTOMapper;
import com.example.gardenedennft.favoriteartwork.repo.FavoriteArtWorkRepo;
import com.example.gardenedennft.favoriteartwork.service.FavoriteArtWorkService;
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
import java.util.*;
import java.util.stream.Collectors;

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

    private final OwnerResponse ownerResponse;

    private final ArtworkResponse artworkResponse;

    private final ArtworkListDTOMapper artworkListDTOMapper;

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
    public void artistSignIn(ArtistAuthenticationRequest request) {
        String email = request.getEmail();
        Artist artist = findArtistByEmail(email);

        if(!checkEmail(email)) {
            Artist artistSave = artistResponse.save(Artist.builder()
                    .email(email)
                    .build());

            Owner owner = Owner.builder()
                            .artist(artistSave)
                            .wallet_address(request.getWalletAddress())
                            .status(true)
                            .build();
            ownerResponse.save(owner);
        }else{
            Boolean ownerExists = ownerResponse.existsOwnerByWalletAddress(request.getWalletAddress());

            if(!ownerExists){
                Owner owner = Owner.builder()
                        .artist(artist)
                        .wallet_address(request.getWalletAddress())
                        .status(true)
                        .build();
                ownerResponse.save(owner);
            }
        }

        String tokenConfirm = String.valueOf(UUID.randomUUID());

        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(tokenConfirm)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .artist(artist)
                .build();

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = baseUrlService.getBaseUrl() + "/api/v1/gardeneden/auth/verify?token="+tokenConfirm;

        emailService.send(
                email,
                emailService.buildEmailVerify(email,link,false),
                "Verify"
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
    public void updateArtist(ArtistRequest artistRequest) {
        if(!checkEmail(artistRequest.getEmail())){
            throw new ResourceNotFoundException("Artist not found with email " + artistRequest.getEmail());
        }
        Artist updateArtist = Artist.builder()
                .name(artistRequest.getName())
                .image_url(artistRequest.getImage_url())
                .bio(artistRequest.getBio())
                .symbol(artistRequest.getSymbol())
                .discord_url(artistRequest.getDiscord_url())
                .twitter_url(artistRequest.getTwitter_url())
                .website_url(artistRequest.getWebsite_url())
                .telegram_url(artistRequest.getTelegram_url())
                .build();

        artistResponse.findArtistByEmail(artistRequest.getEmail()).ifPresent(existingArtist -> {
            Field[] fields = Artist.class.getDeclaredFields();

            for (Field field: fields) {
                try{
                    field.setAccessible(true);
                    Object newValue = field.get(updateArtist);
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
    public ArtistRepo findAllArtistsByTrending() {
        List<Artist> allArtists = artistResponse.findAll();

        List<ArtistDTO> trendingArtists = allArtists.stream()
                .map(this::calculateArtistStats)
                .collect(Collectors.toList());

        return ArtistRepo.builder()
                .listResult(trendingArtists)
                .build();
    }

    private ArtistDTO calculateArtistStats(Artist artist) {
        List<ArtworkDTO> artworkDTOList = artworkListDTOMapper.apply(
                artworkResponse.findArtworkByIdArtistAndStatus(artist.getId(), SystemConstant.STATUS_NFT_ACTIVE)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with id artist " + artist.getId())));

        double floorPrice = 0.0;
        double buyNow = 0.0;
        double sellNow = 0.0;
        int listed = 0;
        int supply = 0;
        double totalPrice = 0.0;
        double volume24h = 0.0;
        double allVolume = 0.0;
        double sales24h = 0.0;
        double allSales = 0.0;
        double percentListed = 0;

        for (ArtworkDTO artworkDTO : artworkDTOList) {
            if (artworkDTO.getPrice() != null) {
                totalPrice += artworkDTO.getPrice();
                listed++;
            }

            if(artworkDTO.getSupply() != null){
                supply += artworkDTO.getSupply();
            }

        }

        if(listed != 0){
            floorPrice = Math.round((totalPrice / listed) * 100.0) / 100.0;
        }

        if (supply != 0) {
            percentListed = (double) listed / supply * 100;
        }

        return ArtistDTO.builder()
                .id(artist.getId())
                .name(artist.getName())
                .symbol(artist.getSymbol())
                .email(artist.getEmail())
                .image_url(artist.getImage_url())
                .discord_url(artist.getDiscord_url())
                .twitter_url(artist.getTwitter_url())
                .telegram_url(artist.getTelegram_url())
                .website_url(artist.getWebsite_url())
                .bio(artist.getBio())
                .status(artist.getStatus())
                .floorPrice(floorPrice)
                .buyNow(buyNow)
                .sellNow(sellNow)
                .listed(listed)
                .supply(supply)
                .percentListed(percentListed)
                .totalPrice(totalPrice)
                .volume24h(volume24h)
                .allVolume(allVolume)
                .sales24h(sales24h)
                .allSales(allSales)
                .build();
    }

    public int countArtworksByArtist(Artist artist){
        int artworkCount = 0;
        List<Owner> allOwners = ownerResponse.findAllByArtist(artist).
                    orElseThrow( () -> new ResourceNotFoundException("Not found owner with artist "+artist.getId()));

        for (Owner owner : allOwners) {
                artworkCount += owner.getArtworks().size();
        }
        return artworkCount;
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

        List<OwnerDTO> ownerDTOList = ownerService.findAllOwnerByIdArtist(artist.getId());

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
