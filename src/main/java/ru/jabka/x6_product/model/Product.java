package ru.jabka.x6_product.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private Long id;
    private String name;
    private Integer price;
    private String producer;
    private String category;
    private String description;
    private Integer in_stock;
    private Integer is_active;
}