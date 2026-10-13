package com.example.ecommerce.order_service.service;

import com.example.ecommerce.order_service.clients.InventoryOpenFeignClient;
import com.example.ecommerce.order_service.dto.OrderRequestDTO;
import com.example.ecommerce.order_service.entity.Order;
import com.example.ecommerce.order_service.entity.OrderItem;
import com.example.ecommerce.order_service.entity.OrderStatus;
import com.example.ecommerce.order_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;

    public List<OrderRequestDTO> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderRequestDTO.class))
                .toList();
    }

    public OrderRequestDTO getOrderById(Long id) {
        log.info("Fetching order id: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find order: " + id));

        return modelMapper.map(order, OrderRequestDTO.class);
    }

    //@Retry(name = "InventoryRetry", fallbackMethod = "createOrderFallback")
    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "createOrderFallback")
    //@RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "createOrderFallback")
    public OrderRequestDTO createOrder(OrderRequestDTO orderRequestDTO) {
        log.info("Calling the create order method");
        Double totalPrice = inventoryOpenFeignClient.reduceStocks(orderRequestDTO);

        Order order = modelMapper.map(orderRequestDTO, Order.class);
        for(OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrder(order);
        }

        order.setTotalPrice(totalPrice);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderRequestDTO.class);
    }

    public OrderRequestDTO createOrderFallback(OrderRequestDTO orderRequestDTO, Throwable throwable) {
        log.error("Fallback occurred due to: {}", throwable.getMessage());
        return new OrderRequestDTO();
    }
}
