package com.example.gardenedennft.artwork.service.Impl;

import com.example.gardenedennft.TransactionType.TransactionType;
import com.example.gardenedennft.TransactionType.TransactionTypeService;
import com.example.gardenedennft.artist.entity.Artist;
import com.example.gardenedennft.artist.entity.request.ArtistRequest;
import com.example.gardenedennft.artist.mapper.ArtistDTOMapper;
import com.example.gardenedennft.artist.repo.ArtistRepo;
import com.example.gardenedennft.artist.service.ArtistService;
import com.example.gardenedennft.artwork.dto.ArtworkDTO;
import com.example.gardenedennft.artwork.dto.response.ArtworkTransactionAttributeDTO;
import com.example.gardenedennft.artwork.entity.Artwork;
import com.example.gardenedennft.artwork.entity.request.ArtworkArtistRequest;
import com.example.gardenedennft.artwork.entity.request.ArtworkConditionRequest;
import com.example.gardenedennft.artwork.entity.request.ArtworkRequest;
import com.example.gardenedennft.artwork.entity.response.ArtworkResponse;
import com.example.gardenedennft.artwork.mapper.ArtworkDTOMapper;
import com.example.gardenedennft.artwork.mapper.ArtworkListDTOMapper;
import com.example.gardenedennft.artwork.repo.ArtworkRepo;
import com.example.gardenedennft.artwork.service.ArtworkService;
import com.example.gardenedennft.attribute.entity.Attribute;
import com.example.gardenedennft.attribute.dto.AttributeDTO;
import com.example.gardenedennft.attribute.mapper.AttributeDTOMapper;
import com.example.gardenedennft.attribute.repo.AttributeRepo;
import com.example.gardenedennft.constant.SystemConstant;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import com.example.gardenedennft.favoriteartwork.dto.FavoriteArtWorkDTO;
import com.example.gardenedennft.favoriteartwork.service.FavoriteArtWorkService;
import com.example.gardenedennft.historycreatenft.HistoryCreateNFTService;
import com.example.gardenedennft.owner.dto.OwnerDTO;
import com.example.gardenedennft.owner.entity.Owner;
import com.example.gardenedennft.owner.repo.OwnerRepo;
import com.example.gardenedennft.owner.service.OwnerService;
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
public class ArtworkServiceImpl implements ArtworkService {
    @PersistenceContext
    private EntityManager entityManager;

    private final ArtworkRepo artworkRepo;

    private final TransactionService transactionService;

    private final TransactionTypeService transactionTypeService;

    private final HistoryCreateNFTService historyCreateNFTService;

    private final ArtistRepo artistRepo;

    private final ArtistService artistService;

    private final ArtistDTOMapper artistDTOMapper;

    private final OwnerRepo ownerRepo;

    private final OwnerService ownerService;

    private final AttributeRepo attributeRepo;

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

        Boolean ownerExists = ownerRepo.existsOwnerByIdArtistAndWalletAddress(artistRequest.getId_artist(),artworkRequest.getWallet_address());

        Owner owner;
        if(!ownerExists){
            owner = ownerRepo.save(updateOwner);
        }else{
            owner = ownerRepo.findOwnerByIdArtistAndWalletAddress(artistRequest.getId_artist(),artworkRequest.getWallet_address()).get();
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

        Artwork artCreate = artworkRepo.save(artwork);

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
    public ArtworkResponse findArtworkByWalletAddress(String walletAddress) {
        List<Artwork> artwork = artworkRepo.findArtworkByWalletAddress(walletAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with walletAddress "+walletAddress));;

        List<Owner> owners = ownerRepo.findListOwnerByWalletAddress(walletAddress)
                .orElseThrow(() -> new ResourceNotFoundException(("Not Found Find owner with wallet address "+walletAddress)));

        List<Artist> artistList = new ArrayList<>();

        for(Owner owner: owners){
            Artist artist = artistRepo.findById(owner.artist.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found artist with id "+owner.artist.getId()));
            artistList.add(artist);
        }

        return ArtworkResponse.builder()
                .listResult(mapToListArtworkDTO(artwork, artistList)
                                .stream()
                                .toList())
                                .build();
    }

    @Override
    public ArtworkResponse findArtworkByWalletAddressAndByCondition(ArtworkArtistRequest request) {
        List<Artist> artists = new ArrayList<>();

        Artist artist = artistRepo.findArtistBySymbol(request.getSymbol())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found artist with symbol "+request.getSymbol()));
        artists.add(artist);

        Owner owner = ownerRepo.findOwnerByIdArtistAndWalletAddress(artist.getId(),request.getWalletAddress())
                .orElseThrow(() -> new ResourceNotFoundException("Not found owner with id artist "+artist.getId()));

        List<Artwork> artworks = artworkRepo.findArtworkByOwner(owner)
                .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with id owner "+owner.getId()));

        return ArtworkResponse.builder()
                .listResult(mapToListArtworkDTO(artworks, artists)
                        .stream()
                        .toList())
                .build();
    }

    @Override
    public ArtworkResponse findArtworkByEmailAndStatus(String email, Integer status) {
        List<Artwork> artworkListOptional = artworkRepo.findArtworkByEmailAndStatus(email, status)
                .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with email "+email));;

        List<ArtworkDTO> artworksDTO = artworkListDTOMapper.apply(artworkListOptional);

        return ArtworkResponse.builder()
                .listResult(artworksDTO)
                .build();
    }

    @Override
    public ArtworkResponse findArtworkByEmail(String email) {
        List<Artwork> artworkListOptional = artworkRepo.findArtworkByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with email "+email));

        List<ArtworkDTO> artworksDTO = artworkListDTOMapper.apply(artworkListOptional);

        return ArtworkResponse.builder()
                .listResult(artworksDTO)
                .build();
    }

    @Override
    public List<ArtworkDTO> getAllArtworkByIdArtistAndStatus(UUID id, Integer status){
        List<Artwork> artworks = artworkRepo.findArtworkByIdArtistAndStatus(id, status)
                .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with id artist "+id));

        return artworks.stream()
                .map(artwork -> mapToArtworkDTO(artwork, id))
                .collect(Collectors.toList());
    }

    @Override
    public ArtworkResponse findArtworkByIdArtistAndStatusAndCondition(ArtworkConditionRequest request) {

        List<Attribute> attributes = request.getAttributes().stream()
                .flatMap(att -> attributeRepo.findAllAttributeByTraitAndValue(att.getTrait_type(), att.getValue()).stream())
                .collect(Collectors.toList());

        ArtworkConditionRequest artworkConditionRequest = new ArtworkConditionRequest();
        artworkConditionRequest.setId(request.getId());
        artworkConditionRequest.setStatus(request.getStatus());
        artworkConditionRequest.setName(request.getName());
        artworkConditionRequest.setAttributes(attributes);

        List<Artwork> artworks = artworkRepo.findArtworkByIdArtistAndStatusAndCondition(artworkConditionRequest)
                .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with id artist "+artworkConditionRequest.getId()));


        List<ArtworkDTO> artworksDTO = artworks.stream()
                .map(artwork -> mapToArtworkDTO(artwork, request.getId()))
                .collect(Collectors.toList());

        return ArtworkResponse.builder()
                .listResult(artworksDTO)
                .build();
    }

    @Override
    public ArtworkResponse findArtworkByIdArtistAndStatus(UUID id, Integer status) {

        List<ArtworkDTO> artworksDTO = getAllArtworkByIdArtistAndStatus(id, status);

        return ArtworkResponse.builder()
                .listResult(artworksDTO)
                .build();
    }

    private List<ArtworkDTO> mapToListArtworkDTO(List<Artwork> artworks, List<Artist> artists) {
        List<ArtworkDTO> artworkDTOList = new ArrayList<>();

        for(Artist artist: artists){
            for(Artwork artwork: artworks){
                ArtworkDTO artworkDTO = mapToArtworkDTO(artwork, artist.getId());
                artworkDTOList.add(artworkDTO);
            }
        }

        return artworkDTOList;
    }

    private ArtworkDTO mapToArtworkDTO(Artwork artwork, UUID id) {
        List<OwnerDTO> owners = ownerService.findAllOwnerByIdArtist(id);

        Artist artist = artistService.findArtistById(id);

        Transaction transaction = transactionService.findTransactionByArtworkId(artwork.getId());
        List<Attribute> attributes = attributeRepo.findAllAttributeByIdArtwork(artwork.getId())
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
        Artwork artwork = artworkRepo.findArtworkByIdAndEmail(id,email)
                .orElseThrow(() -> new ResourceNotFoundException("Not find artwork with id"+id));
        ArtworkDTO artworkDTO = artworkDTOMapper.apply(artwork);

        Transaction transaction = transactionService.findTransactionByArtworkId(id);
        TransactionDTO transactionDTO = transactionDTOMapper.apply(transaction);

        List<Attribute> attributes = attributeRepo.findAllAttributeByIdArtwork(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not find attribute with id artwork"+id));
        List<AttributeDTO> attributesDTO = attributeDTOMapper.apply(attributes);

        return ArtworkTransactionAttributeDTO.builder()
                .artwork(artworkDTO)
                .transaction(transactionDTO)
                .attributes(attributesDTO)
                .build();
    }

    @Override
    public Artwork findArtworkByEmailAndId(String email, Integer id) {
        return artworkRepo.findArtworkByIdAndEmail(id,email)
                .orElseThrow(() -> new ResourceNotFoundException("Not find artwork with id"+id));
    }

    @Override
    public void updateArtworkById(Artwork artwork) {
        Artwork artworkUpdate =  artworkRepo.findById(artwork.getId())
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

        artworkRepo.save(artworkUpdate);
    }
}
