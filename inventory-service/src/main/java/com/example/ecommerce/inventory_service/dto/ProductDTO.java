package com.example.ecommerce.inventory_service.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private long id;
    private String title;
    private Double price;
    private Integer stock;

}
