package com.example.ecommerce.order_service.controller;

import com.example.ecommerce.order_service.dto.OrderRequestDTO;
import com.example.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
@RefreshScope
public class OrderController {

    @Value("${my.variable}")
    private String myVariable;

    private final OrderService orderService;

    @GetMapping("/helloOrders")     // controller to test comms between services using rest client
    public String helloOrders(@RequestHeader("X-User-Id") Long userId) {
        return "Hello from order-service. user-id is: " + userId + " and myVariable is: " + myVariable;
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDTO> createOrder(
            @RequestBody
            OrderRequestDTO orderRequestDTO
    ) {
        OrderRequestDTO order = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDTO>> getAllOrders() {
        List<OrderRequestDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDTO> getOrderById(@PathVariable Long id) {
        OrderRequestDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
