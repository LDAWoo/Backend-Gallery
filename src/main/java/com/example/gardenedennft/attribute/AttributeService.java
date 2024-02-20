package com.example.gardenedennft.attribute;

import java.util.List;
import java.util.UUID;

public interface AttributeService {
    void addAttribute(AttributeRequest attributeRequest, List<Attribute> attributes);
    Attribute findAttributeById(UUID id);
    List<Attribute> findAttributeByIdArtwork(Integer id);
    void deleteAttributeById(UUID id);

}
