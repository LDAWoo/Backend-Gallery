package com.example.gardenedennft.transaction;

import com.example.gardenedennft.TransactionType.TransactionType;
import com.example.gardenedennft.artwork.Artwork;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@Table(name = "_transaction")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String signature;
    private Double amount;
    private String buyer_wallet_address;
    private String seller_wallet_address;
    private Double fee;
    private Double royalty_amount;
    @ManyToOne
    @JoinColumn(name = "id_transactionType")
    private TransactionType transactionType;
    @ManyToOne
    @JoinColumn(name = "id_artwork")
    private Artwork artwork;
}
