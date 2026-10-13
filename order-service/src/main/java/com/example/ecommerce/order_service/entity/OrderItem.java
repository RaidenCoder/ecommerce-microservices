package com.example.ecommerce.order_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private Long productId;
    private Integer quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
