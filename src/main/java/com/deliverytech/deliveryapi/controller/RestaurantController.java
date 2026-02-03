package com.deliverytech.deliveryapi.controller;

import com.deliverytech.deliveryapi.domain.MenuItem;
import com.deliverytech.deliveryapi.domain.Restaurant;
import com.deliverytech.deliveryapi.dto.CreateMenuItemRequest;
import com.deliverytech.deliveryapi.dto.CreateRestaurantRequest;
import com.deliverytech.deliveryapi.dto.MenuItemResponse;
import com.deliverytech.deliveryapi.dto.RestaurantResponse;
import com.deliverytech.deliveryapi.service.MenuItemService;
import com.deliverytech.deliveryapi.service.RestaurantService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;

    public RestaurantController(RestaurantService restaurantService, MenuItemService menuItemService) {
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public List<RestaurantResponse> listRestaurants() {
        return restaurantService.findAll().stream()
            .map(restaurant -> new RestaurantResponse(restaurant.getId(), restaurant.getName(), restaurant.isActive()))
            .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody CreateRestaurantRequest request) {
        Restaurant restaurant = restaurantService.create(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new RestaurantResponse(restaurant.getId(), restaurant.getName(), restaurant.isActive()));
    }

    @PatchMapping("/{id}/status")
    public RestaurantResponse updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        Restaurant restaurant = restaurantService.toggleStatus(id, active);
        return new RestaurantResponse(restaurant.getId(), restaurant.getName(), restaurant.isActive());
    }

    @GetMapping("/{id}/menu-items")
    public List<MenuItemResponse> listMenuItems(@PathVariable Long id) {
        return menuItemService.findByRestaurant(id).stream()
            .map(item -> new MenuItemResponse(item.getId(), item.getName(), item.getPrice(), item.isAvailable()))
            .collect(Collectors.toList());
    }

    @PostMapping("/{id}/menu-items")
    public ResponseEntity<MenuItemResponse> createMenuItem(@PathVariable Long id,
                                                          @Valid @RequestBody CreateMenuItemRequest request) {
        MenuItem item = menuItemService.create(id, request.getName(), request.getPrice(), request.isAvailable());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new MenuItemResponse(item.getId(), item.getName(), item.getPrice(), item.isAvailable()));
    }
}
