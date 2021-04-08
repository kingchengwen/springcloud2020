package com.ccb.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    public String paymentInfoOk(Integer id) {
        return "PaymentFallbackService fall back - paymentInfoOk,😭";
    }

    public String paymentInfoTimeout(Integer id) {
        return "PaymentFallbackService fall back - paymentInfoTimeout,😭";
    }
}
