package com.walletproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "wallet")
@NoArgsConstructor
public class Wallet {

    @Id
    private UUID id;

    private BigDecimal balance;

    @Version
    private Long version;

}