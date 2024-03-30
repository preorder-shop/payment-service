package com.example.paymentservice.client;

import com.example.paymentservice.domain.dto.GetCheckProductIdAndTypeReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "productService")
public interface ProductServiceClient {

    @GetMapping("/internal/check-product") // 예약 상품 + 일반 상품 존재 여부 확인
    public void checkProduct(@RequestBody GetCheckProductIdAndTypeReq getCheckProductIdAndTypeReq);
}
