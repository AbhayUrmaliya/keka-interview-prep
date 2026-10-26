package com.keka.interview.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Domain entity. Structured like a DB-backed model even though storage is in-memory.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String id;
    private String customerId;
    private BigDecimal amount;
    private String currency;
    private OrderStatus status;
    private String paymentReference;
    private String failureReason;
    private Instant createdAt;
    private Instant updatedAt;

    public enum OrderStatus {
        CREATED,
        PAYMENT_PENDING,
        PAID,
        FAILED
    }
}
