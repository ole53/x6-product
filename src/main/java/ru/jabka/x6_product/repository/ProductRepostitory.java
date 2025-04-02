package ru.jabka.x6_product.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.x6_product.model.Product;
import ru.jabka.x6_product.repository.mapper.ProductMapper;
import ru.jabka.x6_product.exception.BadRequestException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepostitory {

    private final ProductMapper productMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT = """
            INSERT INTO x6_application.product (name, price, producer, category, description, in_stock, is_active)
            VALUES (:name, :price, :producer, :category, :description, :in_stock, :is_active)
            RETURNING *;
            """;

    private static final String UPDATE = """
            UPDATE x6_application.product p
            SET p.name = :name, p.price = :price, p.producer = :producer, p.category = :category,
                p.description = :description, p.in_stock = :in_stock, p.is_active = :is_active
            WHERE p.id = :id
            RETURNING *;
            """;

    private static final String GET_BY_ID = """
            SELECT * FROM x6_application.product p
            WHERE p.id = :id and p.is_active = 1;
            """;

    private static final String DELETE = """
            UPDATE x6_application.product p
            SET p.is_active = 0
            WHERE p.id = :id
            RETURNING *;
            """;

    private static final String EXIST = """
            SELECT * FROM x6_application.product p
            WHERE p.id in (:id) and p.is_active = 1;
            """;

    @Transactional(rollbackFor = Exception.class)
    public Product insert(final Product product) {
        return jdbcTemplate.queryForObject(INSERT, productToSql(product), productMapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public Product update(final Product product) {
        return jdbcTemplate.queryForObject(UPDATE, productToSql(product), productMapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public Product delete(final Product product) {
        return jdbcTemplate.queryForObject(DELETE, productToSql(product), productMapper);
    }

    @Transactional(readOnly = true)
    public Product getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), productMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Продукт с id %d не найден", id));
        }
    }

    @Transactional(readOnly = true)
    public List<Product> exist(final List<Long> id) {
        try {
            return jdbcTemplate.queryForList(EXIST, new MapSqlParameterSource("id", id), Product.class);
        } catch (Exception e) {
            throw new BadRequestException("Не найдено ни одного продукта!");
        }
    }

    private MapSqlParameterSource productToSql(final Product product) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", product.getId());
        params.addValue("name", product.getName());
        params.addValue("price", product.getPrice());
        params.addValue("producer", product.getProducer());
        params.addValue("category", product.getCategory());
        params.addValue("description", product.getDescription());
        params.addValue("in_stock", product.getIn_stock());
        params.addValue("is_active", product.getIs_active());

        return params;
    }
}