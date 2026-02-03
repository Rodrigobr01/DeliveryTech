package com.deliverytech.deliveryapi.service;

import com.deliverytech.deliveryapi.domain.Restaurant;
import com.deliverytech.deliveryapi.exception.NotFoundException;
import com.deliverytech.deliveryapi.repository.RestaurantRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant create(String name) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setActive(true);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Restaurante não encontrado"));
    }

    public Restaurant toggleStatus(Long id, boolean active) {
        Restaurant restaurant = findById(id);
        restaurant.setActive(active);
        return restaurantRepository.save(restaurant);
    }
}
