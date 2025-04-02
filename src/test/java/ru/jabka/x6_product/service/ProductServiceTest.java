package ru.jabka.x6_product.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.x6_product.model.Product;
import ru.jabka.x6_product.repository.ProductRepostitory;
import ru.jabka.x6_product.exception.BadRequestException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepostitory productRepostitory;

    @InjectMocks
    private ProductService productService;

    @Test
    void createUser_valid(){
        final Product product = getProduct();
        Mockito.when(productRepostitory.insert(product)).thenReturn(product);

        Product result = productService.create(product);

        assertThat(result).isEqualTo(product);
        verify(productRepostitory).insert(product);
    }

    @Test
    void createUser_withInvalidData_nullName_throwsBadRequestException() {
        final Product product = getProduct();
        product.setName(null);

        final BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> productService.create(product)
        );

        assertEquals("Укажите наименование!", exception.getMessage());
        verify(productRepostitory,never()).insert(any());
    }

    @Test
    void createUser_withInvalidData_nullPrice_throwsBadRequestException() {
        final Product product = getProduct();
        product.setPrice(null);

        final BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> productService.create(product)
        );

        assertEquals("Укажите стоимость продукта!", exception.getMessage());
        verify(productRepostitory,never()).insert(any());
    }

    private Product getProduct() {
        return Product.builder()
                .id(22L)
                .name("tea")
                .price(150)
                .producer("OOO <Jabka>")
                .category("n/a")
                .description("black tea")
                .in_stock(1)
                .is_active(1)
                .build();
    }
}