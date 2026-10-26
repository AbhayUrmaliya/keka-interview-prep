package com.keka.interview.repository;

import com.keka.interview.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface — swap ConcurrentHashMap impl for JPA later without touching services.
 */
public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(String id);

    List<Order> findAll();
}
