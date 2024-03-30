package com.example.paymentservice.client;

import com.example.paymentservice.domain.dto.StockDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stockService")
public interface StockServiceClient {

    @GetMapping("/internal/stocks/{productId}")
    StockDto getProductStock(@PathVariable("productId") String productId);

    @GetMapping("/internal/stocks/decrease/{productId}")
    StockDto decreaseStock(@PathVariable("productId") String productId);

    @GetMapping("/internal/stocks/increase/{productId}")
    StockDto increaseStock(@PathVariable("productId") String productId);
}
