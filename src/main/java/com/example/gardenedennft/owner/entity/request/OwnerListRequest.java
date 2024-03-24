package com.example.gardenedennft.owner.entity.request;

import com.example.gardenedennft.owner.entity.Owner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerListRequest {
    private List<Owner> owners;
}
