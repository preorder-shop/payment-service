package com.example.paymentservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum paymentState {
    DOING("결제 중"),
    COMPLETE("결제 완료"),
    CANCEL("결제 취소");

    private final String text;
}
