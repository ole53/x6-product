package ru.jabka.x6_product.repository.mapper;

import org.springframework.stereotype.Component;
import ru.jabka.x6_product.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductMapper implements RowMapper<Product>{

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Product.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getInt("price"))
                .category(rs.getString("category"))
                .producer(rs.getString("producer"))
                .description(rs.getString("description"))
                .in_stock(rs.getInt("in_stock"))
                .is_active(rs.getInt("is_active"))
                .build();
    }
}