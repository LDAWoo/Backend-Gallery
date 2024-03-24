package com.example.gardenedennft.artist.service.Impl;

import com.example.gardenedennft.artist.entity.response.ArtistResponse;
import com.example.gardenedennft.artist.repo.ArtistRepo;
import com.example.gardenedennft.artist.ArtistSqlNativeResult;
import com.example.gardenedennft.artist.dto.ArtistDTO;
import com.example.gardenedennft.artist.entity.Artist;
import com.example.gardenedennft.artist.entity.request.ArtistAuthenticationRequest;
import com.example.gardenedennft.artist.entity.request.ArtistConditionRequest;
import com.example.gardenedennft.artist.entity.request.ArtistRequest;
import com.example.gardenedennft.artist.mapper.ArtistDTOMapper;
import com.example.gardenedennft.artist.mapper.ArtistOwnerDTOMapper;
import com.example.gardenedennft.artist.service.ArtistService;
import com.example.gardenedennft.artwork.dto.ArtworkDTO;
import com.example.gardenedennft.artwork.mapper.ArtworkListDTOMapper;
import com.example.gardenedennft.artwork.repo.ArtworkRepo;
import com.example.gardenedennft.confirmationtoken.ConfirmationToken;
import com.example.gardenedennft.confirmationtoken.ConfirmationTokenDTO;
import com.example.gardenedennft.confirmationtoken.ConfirmationTokenService;
import com.example.gardenedennft.constant.SystemConstant;
import com.example.gardenedennft.email.EmailService;
import com.example.gardenedennft.exception.ApiRequestException;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import com.example.gardenedennft.favoriteartist.dto.FavoriteArtistDTO;
import com.example.gardenedennft.favoriteartist.mapper.FavoriteListArtistDTOMapper;
import com.example.gardenedennft.favoriteartist.repo.FavoriteArtistRepo;
import com.example.gardenedennft.jwt.JwtService;
import com.example.gardenedennft.owner.entity.Owner;
import com.example.gardenedennft.owner.dto.OwnerDTO;
import com.example.gardenedennft.owner.repo.OwnerRepo;
import com.example.gardenedennft.owner.service.OwnerService;
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
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepo artistRepo;

    private final ConfirmationTokenService confirmationTokenService;

    private final BaseUrlService baseUrlService;

    private final EmailService emailService;

    private final JwtService jwtService;

    private final ArtistDTOMapper artistDTOMapper;

    private final TokenResponse tokenResponse;

    private final ArtistOwnerDTOMapper artistOwnerDTOMapper;

    private final OwnerService ownerService;

    private final OwnerRepo ownerResponse;

    private final ArtworkRepo artworkRepo;

    private final ArtworkListDTOMapper artworkListDTOMapper;

    private final FavoriteArtistRepo favoriteArtistRepo;

    private final FavoriteListArtistDTOMapper favoriteListArtistDTOMapper;

    @Override
    public Artist findArtistByEmail(String email) {
        Artist artist = artistRepo.findArtistByEmail(email)
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
            artistRepo.save(artist);
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
            Artist artistSave = artistRepo.save(Artist.builder()
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
        return artistRepo.existsArtistByEmail(email);
    }

    @Override
    public void updateStatusByEmail(String email) {
        artistRepo.updateStatusByEmail(email);
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

        Artist artist = artistRepo.findOwnerByEmailAndStatus(confirmationToken.getArtist().getEmail(), true)
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
        return artistRepo.findById(id)
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

        artistRepo.findArtistByEmail(artistRequest.getEmail()).ifPresent(existingArtist -> {
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
            artistRepo.save(existingArtist);
        });
    }

    @Override
    public ArtistResponse findAll() {
        return ArtistResponse.builder()
                .listResult(
                        artistRepo.findAll()
                                .stream()
                                .map(artistDTOMapper)
                                .toList()
                ).build();
    }

    @Override
    public ArtistResponse findAllArtistsByTrending() {
        List<Artist> allArtists = artistRepo.findAll();

        List<ArtistDTO> trendingArtists = getAllListArtist(allArtists);

        return ArtistResponse.builder()
                .listResult(trendingArtists)
                .build();
    }

    @Override
    public ArtistResponse findAllArtistsByCondition(ArtistConditionRequest request) {
        List<ArtistSqlNativeResult> results = artistRepo.findAllArtistByCondition(request)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        List<Artist> artistList = new ArrayList<>();

        for (ArtistSqlNativeResult splNative : results) {
            Artist artist = new Artist();
            artist.setId(splNative.getId());
            artist.setName(splNative.getName());
            artist.setImage_url(splNative.getImage_url());
            artist.setSymbol(splNative.getSymbol());
            artist.setDiscord_url(splNative.getDiscord_url());
            artist.setEmail(splNative.getEmail());
            artist.setTwitter_url(splNative.getTwitter_url());
            artist.setBio(splNative.getBio());
            artist.setTelegram_url(splNative.getTelegram_url());
            artistList.add(artist);
        }

        List<ArtistDTO> trendingArtists = getAllListArtist(artistList);
        return ArtistResponse.builder()
                .listResult(trendingArtists)
                .build();
    }

    private List<ArtistDTO> getAllListArtist (List<Artist> artists){
        return artists.stream()
                .map(this::calculateArtistStats)
                .collect(Collectors.toList());
    }

    private ArtistDTO calculateArtistStats(Artist artist) {
        List<ArtworkDTO> artworkDTOList = artworkListDTOMapper.apply(
                artworkRepo.findArtworkByIdArtistAndStatus(artist.getId(), SystemConstant.STATUS_NFT_ACTIVE)
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

        List<FavoriteArtistDTO> favoriteArtistDTOs = favoriteListArtistDTOMapper.apply(favoriteArtistRepo.findFavoriteArtistByArtistId(artist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found favoriteArtist with id artist "+artist.getId())));

        List<OwnerDTO> ownerDTOList = ownerService.findAllOwnerByIdArtist(artist.getId());

        if(listed != 0){
            floorPrice = Math.round((totalPrice / listed) * 100.0) / 100.0;
        }

        if (supply != 0) {
            percentListed = ((double) listed / supply) * 100;
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
                .favoriteArtists(favoriteArtistDTOs)
                .owners(ownerDTOList)
                .build();
    }

    @Override
    public ArtistDTO findArtistBySymbol(String symbol) {
        Artist artist = artistRepo.findArtistBySymbol(symbol)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with symbol: " + symbol));
        return calculateArtistStats(artist);
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

        Optional<Artist> artistOptional = artistRepo.findById(currentToken.getArtist().getId());


        if (artistOptional.isPresent()) {
            ArtistDTO artistDTO = artistDTOMapper.apply(artistOptional.get());
            artistDTO.setToken(token);
            return artistDTO;
        } else {
            throw new ResourceNotFoundException("Artist not found with token " + token);
        }
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
