package com.example.gardenedennft.attribute;

import com.example.gardenedennft.artwork.Artwork;
import com.example.gardenedennft.artwork.ArtworkResponse;
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
public class AttributeServiceImpl implements AttributeService{

    @PersistenceContext
    private EntityManager entityManager;

    private final ArtworkResponse artworkResponse;

    private final AttributeResponse attributeResponse;

    @Override
    public void addAttribute(AttributeRequest attributeRequest, List<Attribute> attributes) {
        Artwork artwork = artworkResponse.findArtworkByIdAndEmail(attributeRequest.get_id_artwork(), attributeRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with id " + attributeRequest.get_id_artwork() + " and email " + attributeRequest.getEmail()));

        List<Attribute> attributeList = findAttributeByIdArtwork(attributeRequest.get_id_artwork());

        for (Attribute attAdd : attributes) {
            boolean isDuplicate = attributeList.stream()
                    .anyMatch(a -> a.getTrait_type().equals(attAdd.getTrait_type()) || a.getValue().equals(attAdd.getValue()));

            if (isDuplicate) {
                throw new ResourceDuplicateException("Attribute already exists: " + attAdd.getTrait_type() + ", " + attAdd.getValue());
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

        attributeResponse.saveAll(attributesToSave);
    }


    @Override
    public Attribute findAttributeById(UUID id) {
        return null;
    }

    @Override
    public List<Attribute> findAttributeByIdArtwork(Integer id) {
        List<Attribute> attributes = attributeResponse.findAllAttributeByIdArtwork(id)
                .orElseThrow(() -> new ResourceNotFoundException(("not find attribute with id artwork "+id)));

        return attributes;
    }

    @Override
    public void deleteAttributeById(UUID id) {
        attributeResponse.deleteById(id);
    }
}
