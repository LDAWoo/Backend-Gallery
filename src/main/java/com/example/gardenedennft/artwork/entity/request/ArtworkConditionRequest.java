package com.example.gardenedennft.artwork.entity.request;

import com.example.gardenedennft.attribute.entity.Attribute;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtworkConditionRequest {
    private UUID id;
    private Integer status;
    private String name;
    private List<Attribute> attributes;
}
