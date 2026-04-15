package com.walletproject.services;

import com.walletproject.models.dto.OperationType;
import com.walletproject.models.dto.WalletOperationDTO;
import com.walletproject.exeptions.InsufficientFundsException;
import com.walletproject.exeptions.WalletNotFoundException;
import com.walletproject.models.Wallet;
import com.walletproject.repositories.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class WalletTxService {
    private final WalletRepository walletRepository;

    public WalletTxService(WalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Wallet doProcess(WalletOperationDTO dto) {

        Wallet wallet = walletRepository.findByIdForUpdate(dto.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        if (dto.getOperationType() == OperationType.DEPOSIT) {
            wallet.setBalance(wallet.getBalance().add(dto.getAmount()));
        }

        if (dto.getOperationType() == OperationType.WITHDRAW) {
            if (wallet.getBalance().compareTo(dto.getAmount()) < 0) {
                throw new InsufficientFundsException("Not enough funds");
            }
            wallet.setBalance(wallet.getBalance().subtract(dto.getAmount()));
        }

        return wallet;
    }
}
