package com.walletproject.services;
import com.walletproject.models.dto.WalletOperationDTO;
import com.walletproject.models.dto.WalletResponseDTO;
import com.walletproject.exeptions.WalletNotFoundException;
import com.walletproject.models.Wallet;
import com.walletproject.repositories.WalletRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import org.slf4j.Logger;

@Service
public class WalletService {
    private final WalletRepository repository;
    private final WalletTxService txService;
    private static Logger log = LoggerFactory.getLogger(WalletService.class);

    public WalletService(WalletRepository repository, WalletTxService txService) {
        this.repository = repository;
        this.txService = txService;
    }

    public WalletResponseDTO createWallet() {
        Wallet wallet = new Wallet();
        wallet.setId(UUID.randomUUID());
        wallet.setBalance(BigDecimal.ZERO);
        repository.save(wallet);
        log.info("Кошелек создан id: " + wallet.getId());
        return mapToDto(wallet);
    }

    public WalletResponseDTO getWalletByUUID(UUID id) {

        Wallet wallet = repository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
        return mapToDto(wallet);
    }

    public WalletResponseDTO process(WalletOperationDTO dto) {
        Wallet wallet = txService.doProcess(dto);
        return mapToDto(wallet);
    }

    public WalletResponseDTO mapToDto(Wallet wallet) {
        WalletResponseDTO dto = new WalletResponseDTO();
        dto.setId(wallet.getId());
        dto.setBalance(wallet.getBalance());
        return dto;
    }
}
