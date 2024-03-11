package com.example.gardenedennft.artwork;

import com.example.gardenedennft.TransactionType.TransactionType;
import com.example.gardenedennft.TransactionType.TransactionTypeService;
import com.example.gardenedennft.artist.*;
import com.example.gardenedennft.artist.entity.request.ArtistRequest;
import com.example.gardenedennft.attribute.Attribute;
import com.example.gardenedennft.attribute.AttributeDTO;
import com.example.gardenedennft.attribute.AttributeDTOMapper;
import com.example.gardenedennft.attribute.AttributeResponse;
import com.example.gardenedennft.constant.SystemConstant;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import com.example.gardenedennft.favoriteartwork.dto.FavoriteArtWorkDTO;
import com.example.gardenedennft.favoriteartwork.entity.FavoriteArtwork;
import com.example.gardenedennft.favoriteartwork.service.FavoriteArtWorkService;
import com.example.gardenedennft.historycreatenft.HistoryCreateNFTService;
import com.example.gardenedennft.owner.*;
import com.example.gardenedennft.transaction.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtworkServiceImpl implements ArtworkService{
    @PersistenceContext
    private EntityManager entityManager;

    private final ArtworkResponse artworkResponse;

    private final TransactionService transactionService;

    private final TransactionTypeService transactionTypeService;

    private final HistoryCreateNFTService historyCreateNFTService;

    private final ArtistService artistService;

    private final ArtistDTOMapper artistDTOMapper;

    private final OwnerResponse ownerResponse;

    private final OwnerService ownerService;

    private final OwnerDTOMapper ownerDTOMapper;

    private final AttributeResponse attributeResponse;

    private final AttributeDTOMapper attributeDTOMapper;

    private final TransactionDTOMapper transactionDTOMapper;

    private final ArtworkDTOMapper artworkDTOMapper;

    private final ArtworkListDTOMapper artworkListDTOMapper;

    private final FavoriteArtWorkService favoriteArtWorkService;


    @Transactional
    @Override
    public void createNFT(ArtistRequest artistRequest, ArtworkRequest artworkRequest, TransactionRequest transactionRequest, List<String> categoryIds) {

        artistService.updateArtist(artistRequest);

        Artist artist = artistService.findArtistById(artistRequest.getId_artist());

        Owner updateOwner = Owner.builder()
                        .artist(artist)
                        .wallet_address(artworkRequest.getWallet_address())
                        .status(true)
                        .build();

        Boolean ownerExists = ownerResponse.existsOwnerByIdArtistAndWalletAddress(artistRequest.getId_artist(),artworkRequest.getWallet_address());

        Owner owner;
        if(!ownerExists){
            owner = ownerResponse.save(updateOwner);
        }else{
            owner = ownerResponse.findOwnerByIdArtistAndWalletAddress(artistRequest.getId_artist(),artworkRequest.getWallet_address()).get();
        }

        Artwork artwork = Artwork.builder()
                .owner(owner)
                .wallet_address(artworkRequest.getWallet_address())
                .name(artworkRequest.getName())
                .symbol(artworkRequest.getSymbolNFT())
                .description(artworkRequest.getDescription())
                .image_url(artworkRequest.getImage_url())
                .chain(artworkRequest.getChain())
                .minted_date(artworkRequest.getMint_date())
                .supply(artworkRequest.getSupply())
                .status(SystemConstant.STATUS_NFT_NO_ACTIVE)
                .createdDate(new Date())
                .lastModifiedDate(new Date())
                .build();

        Artwork artCreate = artworkResponse.save(artwork);

        TransactionType creatorTransactionType = transactionTypeService.getTransactionTypeByType("Creator");

        Transaction transaction = Transaction.builder()
                .artwork(artCreate)
                .transactionType(creatorTransactionType)
                .signature(transactionRequest.getSignature())
                .build();

        transactionService.createTransaction(transaction);

        for (String categoryId : categoryIds) {
            entityManager.createNativeQuery("INSERT INTO _category_artwork (id_artwork, category_id) VALUES (?, ?)")
                    .setParameter(1, artCreate.getId())
                    .setParameter(2, categoryId)
                    .executeUpdate();
        }

        historyCreateNFTService.updateStatusHistoryCreateNFTById(artworkRequest.getId_history_create_nft());

    }

    @Override
    public ArtworkRepo findArtworkByWalletAddress(String walletAddress) {

        List<Artwork> artwork = artworkResponse.findArtworkByWalletAddress(walletAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with walletAddress "+walletAddress));;



        return ArtworkRepo.builder()
                .listResult(artworkListDTOMapper.apply(artwork)
                                .stream()
                                .toList())
                                .build();
    }

    @Override
    public ArtworkRepo findArtworkByEmailAndStatus(String email, Integer status) {
        List<Artwork> artworkListOptional = artworkResponse.findArtworkByEmailAndStatus(email, status)
                .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with email "+email));;

        List<ArtworkDTO> artworksDTO = artworkListDTOMapper.apply(artworkListOptional);

        return ArtworkRepo.builder()
                .listResult(artworksDTO)
                .build();
    }

    @Override
    public ArtworkRepo findArtworkByEmail(String email) {
        List<Artwork> artworkListOptional = artworkResponse.findArtworkByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with email "+email));

        List<ArtworkDTO> artworksDTO = artworkListDTOMapper.apply(artworkListOptional);

        return ArtworkRepo.builder()
                .listResult(artworksDTO)
                .build();
    }

    @Override
    public List<ArtworkDTO> getAllArtworkByIdArtistAndStatus(UUID id, Integer status){
        List<Artwork> artworks = artworkResponse.findArtworkByIdArtistAndStatus(id, status)
                .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with id artist "+id));

        List<ArtworkDTO> artworksDTO = artworks.stream()
                .map(artwork -> mapToArtworkDTO(artwork, id))
                .collect(Collectors.toList());

        return artworksDTO;
    }

    @Override
    public ArtworkRepo findArtworkByIdArtistAndStatus(UUID id, Integer status) {

        List<ArtworkDTO> artworksDTO = getAllArtworkByIdArtistAndStatus(id, status);

        return ArtworkRepo.builder()
                .listResult(artworksDTO)
                .build();
    }

    private ArtworkDTO mapToArtworkDTO(Artwork artwork, UUID id) {
        List<OwnerDTO> owners = ownerService.findAllOwnerByIdArtist(id);

        Artist artist = artistService.findArtistById(id);

        Transaction transaction = transactionService.findTransactionByArtworkId(artwork.getId());
        List<Attribute> attributes = attributeResponse.findAllAttributeByIdArtwork(artwork.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not find attributes with artwork ID " + artwork.getId()));

        List<FavoriteArtWorkDTO> favoriteArtWorks = favoriteArtWorkService.findListFavoriteArtworks(artwork.getId());

        return ArtworkDTO.builder()
                .id(artwork.getId())
                .name(artwork.getName())
                .symbol(artwork.getSymbol())
                .description(artwork.getDescription())
                .image_url(artwork.getImage_url())
                .wallet_address(artwork.getWallet_address())
                .chain(artwork.getChain())
                .price(artwork.getPrice())
                .supply(artwork.getSupply())
                .royalty(artwork.getRoyalty())
                .minted(artwork.getMinted())
                .minted_date(artwork.getMinted_date())
                .status(artwork.getStatus())
                .artist(artistDTOMapper.apply(artist))
                .owner(owners)
                .transaction(transactionDTOMapper.apply(transaction))
                .attributes(attributeDTOMapper.apply(attributes))
                .favoriteArtWorks(favoriteArtWorks)
                .build();
    }

    @Override
    public ArtworkTransactionAttributeDTO findArtworkByIdAndEmail(Integer id, String email) {
        Artwork artwork = artworkResponse.findArtworkByIdAndEmail(id,email)
                .orElseThrow(() -> new ResourceNotFoundException("Not find artwork with id"+id));
        ArtworkDTO artworkDTO = artworkDTOMapper.apply(artwork);

        Transaction transaction = transactionService.findTransactionByArtworkId(id);
        TransactionDTO transactionDTO = transactionDTOMapper.apply(transaction);

        List<Attribute> attributes = attributeResponse.findAllAttributeByIdArtwork(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not find attribute with id artwork"+id));
        List<AttributeDTO> attributesDTO = attributeDTOMapper.apply(attributes);

        ArtworkTransactionAttributeDTO artworkTransactionAttributeDTO = ArtworkTransactionAttributeDTO.builder()
                .artwork(artworkDTO)
                .transaction(transactionDTO)
                .attributes(attributesDTO)
                .build();

        return artworkTransactionAttributeDTO;
    }

    @Override
    public Artwork findArtworkByEmailAndId(String email, Integer id) {
        Artwork artwork = artworkResponse.findArtworkByIdAndEmail(id,email)
                .orElseThrow(() -> new ResourceNotFoundException("Not find artwork with id"+id));

        return artwork;
    }

    @Override
    public void updateArtworkById(Artwork artwork) {
        Artwork artworkUpdate =  artworkResponse.findById(artwork.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with id "+artwork.getId()));

        if (artwork.getName() != null) {
            artworkUpdate.setName(artwork.getName());
        }
        if (artwork.getSymbol() != null) {
            artworkUpdate.setSymbol(artwork.getSymbol());
        }
        if (artwork.getDescription() != null) {
            artworkUpdate.setDescription(artwork.getDescription());
        }
        if (artwork.getImage_url() != null) {
            artworkUpdate.setImage_url(artwork.getImage_url());
        }
        if (artwork.getWallet_address() != null) {
            artworkUpdate.setWallet_address(artwork.getWallet_address());
        }
        if (artwork.getChain() != null) {
            artworkUpdate.setChain(artwork.getChain());
        }
        if (artwork.getPrice() != null) {
            artworkUpdate.setPrice(artwork.getPrice());
        }
        if (artwork.getLastPrice() != null) {
            artworkUpdate.setLastPrice(artwork.getLastPrice());
        }
        if (artwork.getSupply() != null) {
            artworkUpdate.setSupply(artwork.getSupply());
        }
        if (artwork.getRoyalty() != null) {
            artworkUpdate.setRoyalty(artwork.getRoyalty());
        }
        if (artwork.getMinted() != null) {
            artworkUpdate.setMinted(artwork.getMinted());
        }
        if (artwork.getMinted_date() != null) {
            artworkUpdate.setMinted_date(artwork.getMinted_date());
        }
        if (artwork.getView_count() != null) {
            artworkUpdate.setView_count(artwork.getView_count());
        }
        if (artwork.getStatus() != null) {
            artworkUpdate.setStatus(artwork.getStatus());
        }

        artworkResponse.save(artworkUpdate);
    }
}
