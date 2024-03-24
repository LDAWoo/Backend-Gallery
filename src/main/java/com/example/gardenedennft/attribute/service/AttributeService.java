package com.example.gardenedennft.attribute.service;

import com.example.gardenedennft.attribute.entity.Attribute;
import com.example.gardenedennft.attribute.entity.request.AttributeRequest;

import java.util.List;
import java.util.UUID;

public interface AttributeService {
    void addAttribute(AttributeRequest attributeRequest, List<Attribute> attributes);
    Attribute findAttributeById(UUID id);
    List<Attribute> findAttributeByIdArtwork(Integer id);
    void deleteAttributeById(UUID id);

}
