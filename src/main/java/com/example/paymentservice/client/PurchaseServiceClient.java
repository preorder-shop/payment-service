package com.example.paymentservice.client;

import com.example.paymentservice.domain.dto.PurchaseDto;
import com.example.paymentservice.domain.dto.PurchasePreOrderProductReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "purchaseService")
public interface PurchaseServiceClient {

    @PostMapping("/internal/purchase")
    String purchasePreOrderProduct(@RequestBody PurchaseDto purchaseDto);
}
