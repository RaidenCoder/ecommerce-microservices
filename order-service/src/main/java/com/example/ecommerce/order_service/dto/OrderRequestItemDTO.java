package com.example.ecommerce.order_service.dto;

import com.example.ecommerce.order_service.entity.Order;
import lombok.Data;

@Data
public class OrderRequestItemDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
}
