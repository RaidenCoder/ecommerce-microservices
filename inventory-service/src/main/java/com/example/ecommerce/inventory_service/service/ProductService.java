package com.example.ecommerce.inventory_service.service;

import com.example.ecommerce.inventory_service.dto.OrderRequestDTO;
import com.example.ecommerce.inventory_service.dto.OrderRequestItemDTO;
import com.example.ecommerce.inventory_service.dto.ProductDTO;
import com.example.ecommerce.inventory_service.entity.Product;
import com.example.ecommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDTO> getAllInventory() {
        log.info("Log all inventory items: ");
        List<Product> inventories = productRepository.findAll();
        return inventories.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    public ProductDTO getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        Optional<Product> inventory = productRepository.findById(id);
        return inventory.map(item -> modelMapper.map(item, ProductDTO.class))
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    @Transactional
    public double reduceStocks(OrderRequestDTO orderRequestDTO) {
        log.info("Reducing the stocks");
        double totalPrice = 0.0;
        for(OrderRequestItemDTO orderRequestItemDTO : orderRequestDTO.getOrderItems()) {
            Long productId = orderRequestItemDTO.getProductId();
            Integer quantity = orderRequestItemDTO.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product with id: " + productId + ", not found"));

            if(product.getStock() < quantity) {
                throw new RuntimeException("Not enough stock for productId: " + productId);
            }

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            totalPrice += quantity * product.getPrice();
        }

        return totalPrice;
    }
}
