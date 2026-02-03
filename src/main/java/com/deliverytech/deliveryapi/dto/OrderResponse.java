package com.deliverytech.deliveryapi.dto;

import com.deliverytech.deliveryapi.domain.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long id;
    private OrderStatus status;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private Long customerId;
    private Long restaurantId;
    private List<OrderItemResponse> items;

    public OrderResponse(Long id, OrderStatus status, BigDecimal total, LocalDateTime createdAt,
                         Long customerId, Long restaurantId, List<OrderItemResponse> items) {
        this.id = id;
        this.status = status;
        this.total = total;
        this.createdAt = createdAt;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }
}
