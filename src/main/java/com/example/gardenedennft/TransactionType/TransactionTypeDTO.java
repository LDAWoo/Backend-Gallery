package com.example.gardenedennft.TransactionType;

import com.example.gardenedennft.utils.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTypeDTO extends BaseDTO {
    private String type;
}
