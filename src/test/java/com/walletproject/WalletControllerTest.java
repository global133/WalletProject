package com.walletproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletproject.models.Wallet;
import com.walletproject.models.dto.OperationType;
import com.walletproject.models.dto.WalletOperationDTO;
import com.walletproject.repositories.WalletRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WalletRepository repository;

    @Test
    void getWallet_shouldReturnDtoWallet() throws Exception {
        Wallet wallet = new Wallet();
        UUID randomId = UUID.randomUUID();
        wallet.setId(randomId);
        wallet.setBalance(BigDecimal.valueOf(1000));

        repository.save(wallet);

        mockMvc.perform(get("/api/v1/wallets/{id}", wallet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    void process_shouldReturn200() throws Exception {

        Wallet wallet = new Wallet();
        UUID randomId = UUID.randomUUID();
        wallet.setId(randomId);
        wallet.setBalance(BigDecimal.valueOf(1000));

        repository.save(wallet);

        WalletOperationDTO dto = new WalletOperationDTO();
        dto.setWalletId(wallet.getId());
        dto.setOperationType(OperationType.DEPOSIT);
        dto.setAmount(BigDecimal.valueOf(100));

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(1100));
    }
}
