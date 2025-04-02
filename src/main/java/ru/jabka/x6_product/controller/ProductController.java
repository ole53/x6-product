package ru.jabka.x6_product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.jabka.x6_product.model.Product;
import ru.jabka.x6_product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Продукты")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Создать продукт")
    public Product create(@RequestBody final Product product) {
        return productService.create(product);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить продукт по id")
    public Product get(@PathVariable final Long id) {
        return productService.getById(id);
    }

    @GetMapping("/exist/{id}")
    @Operation(summary = "Проверить существование продуктов по списку id")
    public List<Product> exist(@PathVariable final List<Long> id) {
        return productService.exist(id);
    }

    @PatchMapping
    @Operation(summary = "Обновление информации о продукте")
    public Product update(@RequestBody final Product product) {
        return productService.update(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление продукта")
    public Product delete(@PathVariable final Long id) {
        return productService.delete(id);
    }
}