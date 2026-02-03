package com.deliverytech.delivery.service;

import com.deliverytech.delivery.domain.Customer;
import com.deliverytech.delivery.domain.MenuItem;
import com.deliverytech.delivery.domain.OrderEntity;
import com.deliverytech.delivery.domain.OrderItem;
import com.deliverytech.delivery.domain.OrderStatus;
import com.deliverytech.delivery.domain.Restaurant;
import com.deliverytech.delivery.dto.OrderItemRequest;
import com.deliverytech.delivery.exception.BadRequestException;
import com.deliverytech.delivery.exception.NotFoundException;
import com.deliverytech.delivery.repository.OrderRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;

    public OrderService(OrderRepository orderRepository, CustomerService customerService,
                        RestaurantService restaurantService, MenuItemService menuItemService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
    }

    @Transactional
    public OrderEntity create(Long customerId, Long restaurantId, List<OrderItemRequest> items) {
        Customer customer = customerService.findById(customerId);
        Restaurant restaurant = restaurantService.findById(restaurantId);

        if (!restaurant.isActive()) {
            throw new BadRequestException("Restaurante indisponível para pedidos");
        }

        OrderEntity order = new OrderEntity();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequest itemRequest : items) {
            MenuItem menuItem = menuItemService.findById(itemRequest.getMenuItemId());
            if (!menuItem.isAvailable()) {
                throw new BadRequestException("Item do cardápio indisponível");
            }
            if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
                throw new BadRequestException("Item não pertence ao restaurante selecionado");
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(menuItem.getPrice());
            order.getItems().add(orderItem);

            BigDecimal lineTotal = menuItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            total = total.add(lineTotal);
        }

        if (order.getItems().isEmpty()) {
            throw new BadRequestException("Pedido precisa de ao menos um item");
        }

        order.setTotal(total);
        return orderRepository.save(order);
    }

    public OrderEntity findById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
    }

    public List<OrderEntity> findAll() {
        return orderRepository.findAll();
    }
}
