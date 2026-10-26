package com.keka.interview.controller;

import com.keka.interview.dto.PaymentChargeRequest;
import com.keka.interview.dto.PaymentChargeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Local stand-in for an external payment API.
 * Use fail-next / always-fail to trip Hystrix during the interview demo.
 */
@Slf4j
@RestController
@RequestMapping("/mock/payments")
public class MockPaymentController {

    private final AtomicBoolean alwaysFail = new AtomicBoolean(false);
    private final AtomicBoolean failNext = new AtomicBoolean(false);

    @PostMapping("/charge")
    public PaymentChargeResponse charge(@RequestBody PaymentChargeRequest request) {
        if (alwaysFail.get() || failNext.compareAndSet(true, false)) {
            log.warn("Mock payment FAILING for orderId={}", request.getOrderId());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Payment provider down");
        }

        String ref = "pay_" + UUID.randomUUID().toString().substring(0, 8);
        log.info("Mock payment SUCCESS orderId={} ref={}", request.getOrderId(), ref);

        return PaymentChargeResponse.builder()
                .success(true)
                .fallback(false)
                .paymentReference(ref)
                .message("Charged")
                .build();
    }

    /** POST /mock/payments/fail-next — next charge call will 503 */
    @PostMapping("/fail-next")
    public Map<String, Object> failNext() {
        failNext.set(true);
        return Collections.<String, Object>singletonMap("failNext", true);
    }

    /** POST /mock/payments/always-fail?enabled=true|false */
    @PostMapping("/always-fail")
    public Map<String, Object> alwaysFail(@RequestParam boolean enabled) {
        alwaysFail.set(enabled);
        return Collections.<String, Object>singletonMap("alwaysFail", enabled);
    }
}
