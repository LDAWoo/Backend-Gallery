package com.example.gardenedennft.attribute.service.Impl;

import com.example.gardenedennft.artwork.entity.Artwork;
import com.example.gardenedennft.artwork.repo.ArtworkRepo;
import com.example.gardenedennft.attribute.entity.Attribute;
import com.example.gardenedennft.attribute.entity.request.AttributeRequest;
import com.example.gardenedennft.attribute.repo.AttributeRepo;
import com.example.gardenedennft.attribute.service.AttributeService;
import com.example.gardenedennft.exception.ResourceDuplicateException;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttributeServiceImpl implements AttributeService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ArtworkRepo artworkRepo;

    private final AttributeRepo attributeRepo;

    @Override
    public void addAttribute(AttributeRequest attributeRequest, List<Attribute> attributes) {
        Artwork artwork = artworkRepo.findArtworkByIdAndEmail(attributeRequest.get_id_artwork(), attributeRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with id " + attributeRequest.get_id_artwork() + " and email " + attributeRequest.getEmail()));

        List<Attribute> attributeList = findAttributeByIdArtwork(attributeRequest.get_id_artwork());

        for (Attribute attAdd : attributes) {
            boolean isDuplicate = attributeList.stream()
                    .anyMatch(a -> a.getTrait_type().equals(attAdd.getTrait_type()));

            if (isDuplicate) {
                throw new ResourceDuplicateException("Attribute already exists: " + attAdd.getTrait_type());
            }
        }

        List<Attribute> attributesToSave = attributes.stream()
                .filter(attAdd -> !attributeList.stream()
                        .anyMatch(a -> a.getTrait_type().equals(attAdd.getTrait_type()) && a.getValue().equals(attAdd.getValue())))
                .map(attAdd -> Attribute.builder()
                        .trait_type(attAdd.getTrait_type())
                        .value(attAdd.getValue())
                        .artwork(artwork)
                        .build())
                .collect(Collectors.toList());

        attributeRepo.saveAll(attributesToSave);
    }


    @Override
    public Attribute findAttributeById(UUID id) {
        return null;
    }

    @Override
    public List<Attribute> findAttributeByIdArtwork(Integer id) {
        return attributeRepo.findAllAttributeByIdArtwork(id)
                .orElseThrow(() -> new ResourceNotFoundException(("not find attribute with id artwork "+id)));
    }

    @Override
    public void deleteAttributeById(UUID id) {
        attributeRepo.deleteById(id);
    }
}
