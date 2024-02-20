package com.example.gardenedennft.TransactionType;

import com.example.gardenedennft.utils.BaseResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class TransactionRepo extends BaseResponse<TransactionTypeDTO> {
}
