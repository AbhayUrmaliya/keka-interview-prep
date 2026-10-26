package com.keka.interview.service;

import com.keka.interview.client.PaymentGatewayClient;
import com.keka.interview.dto.CreateOrderRequest;
import com.keka.interview.dto.OrderResponse;
import com.keka.interview.dto.PaymentChargeRequest;
import com.keka.interview.dto.PaymentChargeResponse;
import com.keka.interview.model.Order;
import com.keka.interview.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentGatewayClient paymentGatewayClient;

    public OrderResponse createOrder(CreateOrderRequest request) {
        Instant now = Instant.now();
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .customerId(request.getCustomerId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status(Order.OrderStatus.CREATED)
                .createdAt(now)
                .updatedAt(now)
                .build();

        orderRepository.save(order);
        log.info("Order created id={} customerId={}", order.getId(), order.getCustomerId());

        return chargePayment(order);
    }

    public OrderResponse getOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return OrderResponse.from(order);
    }

    public List<OrderResponse> listOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    private OrderResponse chargePayment(Order order) {
        order.setStatus(Order.OrderStatus.PAYMENT_PENDING);
        order.setUpdatedAt(Instant.now());
        orderRepository.save(order);

        PaymentChargeRequest chargeRequest = PaymentChargeRequest.builder()
                .orderId(order.getId())
                .customerId(order.getCustomerId())
                .amount(order.getAmount())
                .currency(order.getCurrency())
                .build();

        PaymentChargeResponse payment = paymentGatewayClient.charge(chargeRequest);

        if (payment.isSuccess() && !payment.isFallback()) {
            order.setStatus(Order.OrderStatus.PAID);
            order.setPaymentReference(payment.getPaymentReference());
            order.setFailureReason(null);
        } else {
            order.setStatus(Order.OrderStatus.FAILED);
            order.setFailureReason(payment.getMessage());
        }
        order.setUpdatedAt(Instant.now());
        orderRepository.save(order);

        log.info("Order payment settled id={} status={}", order.getId(), order.getStatus());
        return OrderResponse.from(order);
    }
}
