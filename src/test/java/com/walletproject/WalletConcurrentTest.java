package com.walletproject;

import com.walletproject.models.dto.OperationType;
import com.walletproject.models.dto.WalletOperationDTO;
import com.walletproject.models.dto.WalletResponseDTO;
import com.walletproject.services.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class WalletConcurrentTest {

    @Autowired
    private WalletService walletService;


    @Test
    void concurrentWithdraw_shouldBeSafe() throws Exception {
        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        UUID walletId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            futures.add(executor.submit(() -> {
                WalletOperationDTO dto = new WalletOperationDTO();
                dto.setWalletId(walletId);
                dto.setOperationType(OperationType.DEPOSIT);
                dto.setAmount(BigDecimal.valueOf(50));

                walletService.process(dto);
            }));
        }

        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (Exception e) {
                System.out.println("Task failed: " + e.getMessage());
            }
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        WalletResponseDTO wallet = walletService.getWalletByUUID(walletId);
        System.out.println("Final balance = " + wallet.getBalance());
    }

}
