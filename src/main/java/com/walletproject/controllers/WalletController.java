package com.walletproject.controllers;

import com.walletproject.models.dto.WalletOperationDTO;
import com.walletproject.models.dto.WalletResponseDTO;
import com.walletproject.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    @PostMapping("/api/v1/wallets/create")
    public ResponseEntity<WalletResponseDTO> createWallet() {
        WalletResponseDTO dto = walletService.createWallet();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }

    @GetMapping("/api/v1/wallets/{id}")
    public WalletResponseDTO getWalletById (@PathVariable UUID id){
        WalletResponseDTO resultDto = walletService.getWalletByUUID(id);
        return resultDto;
    }

    @PostMapping("/api/v1/wallet")
    public WalletResponseDTO proccess (@RequestBody WalletOperationDTO dto){
        WalletResponseDTO resultDto = walletService.process(dto);
        return resultDto;
    }
}
