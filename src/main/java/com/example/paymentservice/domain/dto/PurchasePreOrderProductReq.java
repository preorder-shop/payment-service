package com.example.paymentservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PurchasePreOrderProductReq {
    private String userId;
    private String productId;
}
