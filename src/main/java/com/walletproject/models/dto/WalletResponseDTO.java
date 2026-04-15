package com.walletproject.models.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class WalletResponseDTO {
    private UUID id;
    private BigDecimal balance;
}
