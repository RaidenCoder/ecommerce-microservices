package com.example.ecommerce.inventory_service.controller;

import com.example.ecommerce.inventory_service.clients.OrdersFeignClient;
import com.example.ecommerce.inventory_service.dto.OrderRequestDTO;
import com.example.ecommerce.inventory_service.dto.ProductDTO;
import com.example.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    private final OrdersFeignClient ordersFeignClient;

    @GetMapping("/fetchOrderService")
    public String fetchOrderService() {
        ServiceInstance orderService = discoveryClient.getInstances("order-service").get(0);

//        return restClient.get()
//                .uri(orderService.getUri() + "/orders/core/helloOrders")
//                .retrieve()
//                .body(String.class);

        return ordersFeignClient.helloOrders();
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllInventory() {
        List<ProductDTO> inventories = productService.getAllInventory();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getInventoryById(@PathVariable Long id) {
        ProductDTO inventory = productService.getProductById(id);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDTO orderRequestDTO) {
        Double totalPrice = productService.reduceStocks(orderRequestDTO);
        return ResponseEntity.ok(totalPrice);
    }
}
