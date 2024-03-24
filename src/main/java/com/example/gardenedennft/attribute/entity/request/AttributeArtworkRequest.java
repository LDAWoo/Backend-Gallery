package com.example.gardenedennft.attribute.entity.request;

import com.example.gardenedennft.attribute.entity.Attribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeArtworkRequest {
    private AttributeRequest attributeRequest;
    private List<Attribute> attributes;
}
