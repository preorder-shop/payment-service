package com.example.paymentservice.service;

import com.example.paymentservice.client.PurchaseServiceClient;
import com.example.paymentservice.client.StockServiceClient;
import com.example.paymentservice.domain.dto.CreatePaymentReq;
import com.example.paymentservice.domain.dto.PurchaseDto;
import com.example.paymentservice.domain.dto.PurchasePreOrderProductReq;
import com.example.paymentservice.domain.dto.StockDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PurchaseServiceClient purchaseServiceClient;
    private final StockServiceClient stockServiceClient;
    public String createPayment(String userId, CreatePaymentReq createPaymentReq, double prob) {
   //     log.error("결제 API 진입 -> 재고 하나 감소 ");
        StockDto decreaseStock = stockServiceClient.decreaseStock(createPaymentReq.getProductId());
   //     log.info("재고 -1  : {}",decreaseStock.getStock());
   //     log.info("PaymentService createPayment prob : {}",prob);

        if (0.2 > prob) { // 요청중 20%는 취소 처리.
       //     log.error("사용자 요청으로 결제 취소");
            StockDto increaseStock = stockServiceClient.increaseStock(createPaymentReq.getProductId());
    //        log.info("재고 + 1 : {}",increaseStock.getStock());
            return "fail-1";
        }

        String result = "";
        PurchaseDto request = new PurchaseDto(userId,createPaymentReq.getProductId());
        try{
            result = purchaseServiceClient.purchasePreOrderProduct(request);
        }catch (FeignException e){
     //       log.info("feign error로 결제 취소");
            StockDto increaseStock = stockServiceClient.increaseStock(createPaymentReq.getProductId());
     //       log.info("재고 + 1 : {}",increaseStock.getStock());
            log.error(e.getMessage());
            result = "feign error";
        }

        return result;

    }

//    public boolean checkProductAndType(String productId) {
//        // todo: product 상품 존재 확인
//
//    }

    public boolean checkProductStock(String productId) {
        StockDto productStock = stockServiceClient.getProductStock(productId);
        int stock = productStock.getStock();
        if (stock>0){
            return true;
        }
        return false;
    }
}
