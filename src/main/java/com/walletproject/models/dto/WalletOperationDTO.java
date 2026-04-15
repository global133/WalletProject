package com.walletproject.models.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletOperationDTO {
    @NonNull
    private UUID walletId;
    private OperationType operationType;

    @NonNull
    private BigDecimal amount;
}

