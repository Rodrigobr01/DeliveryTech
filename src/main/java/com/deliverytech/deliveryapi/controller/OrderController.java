package com.deliverytech.deliveryapi.controller;

import com.deliverytech.deliveryapi.domain.OrderEntity;
import com.deliverytech.deliveryapi.dto.CreateOrderRequest;
import com.deliverytech.deliveryapi.dto.OrderItemResponse;
import com.deliverytech.deliveryapi.dto.OrderResponse;
import com.deliverytech.deliveryapi.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderResponse> listOrders() {
        return orderService.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return toResponse(orderService.findById(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderEntity order = orderService.create(request.getCustomerId(), request.getRestaurantId(), request.getItems());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(order));
    }

    private OrderResponse toResponse(OrderEntity order) {
        List<OrderItemResponse> items = order.getItems().stream()
            .map(item -> new OrderItemResponse(
                item.getMenuItem().getId(),
                item.getMenuItem().getName(),
                item.getQuantity(),
                item.getPrice()))
            .collect(Collectors.toList());
        return new OrderResponse(
            order.getId(),
            order.getStatus(),
            order.getTotal(),
            order.getCreatedAt(),
            order.getCustomer().getId(),
            order.getRestaurant().getId(),
            items);
    }
}
