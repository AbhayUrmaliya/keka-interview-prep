package com.keka.interview.client;

import com.keka.interview.dto.PaymentChargeRequest;
import com.keka.interview.dto.PaymentChargeResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Outbound payment call wrapped with Hystrix.
 * Fallback returns a degraded response so the main flow can decide (retry / mark FAILED / queue).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentGatewayClient {

    private final RestTemplate restTemplate;

    @Value("${external.payment.base-url}")
    private String baseUrl;

    @Value("${external.payment.path}")
    private String path;

    @HystrixCommand(
            groupKey = "PaymentGateway",
            commandKey = "charge",
            fallbackMethod = "chargeFallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }
    )
    public PaymentChargeResponse charge(PaymentChargeRequest request) {
        String url = baseUrl + path;
        log.info("Calling payment gateway orderId={} url={}", request.getOrderId(), url);

        PaymentChargeResponse response = restTemplate.postForObject(url, request, PaymentChargeResponse.class);
        if (response == null) {
            throw new IllegalStateException("Empty response from payment gateway");
        }
        log.info("Payment gateway success orderId={} ref={}", request.getOrderId(), response.getPaymentReference());
        return response;
    }

    /**
     * Invoked when the primary call fails or the circuit is OPEN.
     * Keep this fast and side-effect free.
     */
    @SuppressWarnings("unused")
    public PaymentChargeResponse chargeFallback(PaymentChargeRequest request, Throwable cause) {
        log.warn("Payment gateway fallback orderId={} reason={}",
                request.getOrderId(),
                cause == null ? "circuit-open-or-unknown" : cause.getMessage());

        return PaymentChargeResponse.builder()
                .success(false)
                .fallback(true)
                .paymentReference(null)
                .message("Payment service unavailable. Fallback activated: "
                        + (cause == null ? "circuit open" : cause.getMessage()))
                .build();
    }
}
