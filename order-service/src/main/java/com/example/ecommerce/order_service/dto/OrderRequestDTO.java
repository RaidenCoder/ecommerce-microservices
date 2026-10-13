package com.example.ecommerce.order_service.dto;

import com.example.ecommerce.order_service.entity.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequestDTO {
    private long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal totalPrice;
    private List<OrderRequestItemDTO> orderItems;
}
