package ru.jabka.x6_product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.x6_product.exception.BadRequestException;
import ru.jabka.x6_product.model.Product;
import ru.jabka.x6_product.repository.ProductRepostitory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepostitory productRepostitory;

    public Product create(final Product product) {
        validate(product);

        return productRepostitory.insert(product);
    }

    @Cacheable(value = "product", key = "#id")
    public Product getById(final Long id) {
        return productRepostitory.getById(id);
    }

    @Cacheable(value = "product", key = "#id")
    public List<Product> exist(final List<Long> id) { return productRepostitory.exist(id); }

    @CachePut(value = "product", key = "#product.id")
    public Product update(final Product product) {
        validate(product);

        return productRepostitory.update(product);
    }

    @CacheEvict(value = "product", key = "#id")
    public Product delete(final Long id) {
        final Product product = getById(id);
        return productRepostitory.delete(product);
    }

    private void validate(final Product product) {
        if (product == null) {
            throw new BadRequestException("Введите информацию о продукте");
        }
        if (!StringUtils.hasText(product.getName())) {
            throw new BadRequestException("Укажите наименование!");
        }
        if (!StringUtils.hasText(product.getPrice().toString())) {
            throw new BadRequestException("Укажите стоимость продукта!");
        }
        if (!StringUtils.hasText(product.getProducer())) {
            throw new BadRequestException("Необходимо указать поставщика продукта!");
        }
    }
}