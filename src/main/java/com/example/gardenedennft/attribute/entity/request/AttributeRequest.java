package com.example.gardenedennft.attribute.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttributeRequest {
    private Integer _id_artwork;
    private String email;
}
