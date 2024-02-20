package com.example.gardenedennft.TransactionType;

import com.example.gardenedennft.transaction.Transaction;
import com.example.gardenedennft.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "_transactionType")
public class TransactionType extends BaseEntity {
    private String type;
    @OneToMany(mappedBy = "transactionType", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

}
