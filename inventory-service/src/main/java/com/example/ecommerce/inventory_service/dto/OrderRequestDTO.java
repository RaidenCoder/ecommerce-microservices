package com.example.ecommerce.inventory_service.dto;

import com.example.ecommerce.inventory_service.entity.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequestDTO {

    private List<OrderRequestItemDTO> orderItems;

}
