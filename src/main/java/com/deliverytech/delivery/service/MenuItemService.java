package com.deliverytech.delivery.service;

import com.deliverytech.delivery.domain.MenuItem;
import com.deliverytech.delivery.domain.Restaurant;
import com.deliverytech.delivery.exception.NotFoundException;
import com.deliverytech.delivery.repository.MenuItemRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantService restaurantService;

    public MenuItemService(MenuItemRepository menuItemRepository, RestaurantService restaurantService) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantService = restaurantService;
    }

    public MenuItem create(Long restaurantId, String name, BigDecimal price, boolean available) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        MenuItem item = new MenuItem();
        item.setName(name);
        item.setPrice(price);
        item.setAvailable(available);
        item.setRestaurant(restaurant);
        return menuItemRepository.save(item);
    }

    public List<MenuItem> findByRestaurant(Long restaurantId) {
        restaurantService.findById(restaurantId);
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Item do cardápio não encontrado"));
    }
}
