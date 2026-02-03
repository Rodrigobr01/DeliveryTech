package com.deliverytech.delivery.dto;

import java.math.BigDecimal;

public class OrderItemResponse {

    private Long menuItemId;
    private String name;
    private Integer quantity;
    private BigDecimal price;

    public OrderItemResponse(Long menuItemId, String name, Integer quantity, BigDecimal price) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
