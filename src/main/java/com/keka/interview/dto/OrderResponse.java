package com.keka.interview.dto;

import com.keka.interview.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private String orderId;
    private String customerId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String paymentReference;
    private String failureReason;
    private Instant createdAt;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .customerId(order.getCustomerId())
                .amount(order.getAmount())
                .currency(order.getCurrency())
                .status(order.getStatus().name())
                .paymentReference(order.getPaymentReference())
                .failureReason(order.getFailureReason())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
