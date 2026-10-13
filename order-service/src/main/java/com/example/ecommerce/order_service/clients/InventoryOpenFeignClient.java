package com.example.ecommerce.order_service.clients;

import com.example.ecommerce.order_service.dto.OrderRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", path = "/inventory", url = "${INVENTORY_SERVICE_URI:}")
public interface InventoryOpenFeignClient {

    @PutMapping("/products/reduce-stocks")
    Double reduceStocks(@RequestBody OrderRequestDTO orderRequestDTO);

}
