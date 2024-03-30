package com.example.paymentservice.controller;

import com.example.paymentservice.domain.dto.CreatePaymentReq;
import com.example.paymentservice.service.PaymentService;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {

    public static final LocalTime PRE_ORDER_OPEN_TIME = LocalTime.of(14, 0); // 오후 2시
    public static final LocalTime PRE_ORDER_CLOSE_TIME = LocalTime.of(16, 0); // 오후 4시\
    private final PaymentService paymentService;

    /**
     * 예약 상품 결제 화면 진입 API
     */
    @PostMapping("/pre-order")
    public ResponseEntity<?> paymentPreOrderInit(@RequestHeader("X-USER-ID") String userId,
                                         @RequestBody CreatePaymentReq createPaymentReq) {

   //     log.error("PaymentController paymentPreOrderInit 호출");

        LocalTime registerTime = LocalDateTime.now().toLocalTime(); // 주문 시각

//        if(registerTime.isBefore(PRE_ORDER_CLOSE_TIME)||registerTime.isAfter(PRE_ORDER_CLOSE_TIME)){
//            return ResponseEntity.status(403).body("지금은 주문 시간이 아닙니다.");
//        }

 //       log.info("결제 전에 재고 확인");
        if(!paymentService.checkProductStock(createPaymentReq.getProductId())){
            return ResponseEntity.status(403).body("재고가 모두 소진되었습니다.");
        }
//        log.info("재고 존재");

        double prob = Math.random();

        String result = paymentService.createPayment(userId,createPaymentReq,prob);

        if(result.equals("fail-1")){
            return ResponseEntity.status(403).body("사용자 요청으로 주문 취소");
        }

        if(result.equals("fail-2")){
            return ResponseEntity.status(403).body("결제 정보 오류로 또는 잔액 부족으로 주문 취소");
        }

        if(result.equals("fail-3")){
            return ResponseEntity.status(403).body("재고 부족으로 결제 불가");
        }


        if(result.equals("feign error")){
            return ResponseEntity.status(500).body("서버 에러 발생으로 주문 불가");
        }

        return ResponseEntity.ok().body("주문번호 : "+ result+ " 주문 완료.");
    }


    @PostMapping()// 일반 상품 주문
    public void paymentInit(){

    }
}
