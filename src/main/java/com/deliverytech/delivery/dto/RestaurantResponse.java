package com.deliverytech.delivery.dto;

public class RestaurantResponse {

    private Long id;
    private String name;
    private boolean active;

    public RestaurantResponse(Long id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }
}
