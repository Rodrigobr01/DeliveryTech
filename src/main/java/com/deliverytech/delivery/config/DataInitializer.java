package com.deliverytech.delivery.config;

import com.deliverytech.delivery.domain.Customer;
import com.deliverytech.delivery.domain.MenuItem;
import com.deliverytech.delivery.domain.Restaurant;
import com.deliverytech.delivery.repository.CustomerRepository;
import com.deliverytech.delivery.repository.MenuItemRepository;
import com.deliverytech.delivery.repository.RestaurantRepository;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(RestaurantRepository restaurantRepository,
                                      MenuItemRepository menuItemRepository,
                                      CustomerRepository customerRepository) {
        return args -> {
            if (restaurantRepository.count() > 0) {
                return;
            }

            Restaurant sushiHouse = new Restaurant();
            sushiHouse.setName("Sushi House");
            sushiHouse.setActive(true);
            restaurantRepository.save(sushiHouse);

            MenuItem item1 = new MenuItem();
            item1.setName("Combo Salmão");
            item1.setPrice(new BigDecimal("42.90"));
            item1.setAvailable(true);
            item1.setRestaurant(sushiHouse);
            menuItemRepository.save(item1);

            MenuItem item2 = new MenuItem();
            item2.setName("Temaki Atum");
            item2.setPrice(new BigDecimal("28.50"));
            item2.setAvailable(true);
            item2.setRestaurant(sushiHouse);
            menuItemRepository.save(item2);

            Restaurant pizzaLab = new Restaurant();
            pizzaLab.setName("Pizza Lab");
            pizzaLab.setActive(true);
            restaurantRepository.save(pizzaLab);

            MenuItem item3 = new MenuItem();
            item3.setName("Pizza Margherita");
            item3.setPrice(new BigDecimal("55.00"));
            item3.setAvailable(true);
            item3.setRestaurant(pizzaLab);
            menuItemRepository.save(item3);

            Customer customer = new Customer();
            customer.setName("Ana Souza");
            customer.setEmail("ana.souza@email.com");
            customerRepository.save(customer);
        };
    }
}
