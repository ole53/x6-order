package ru.jabka.x6_order.repository.mapper;

import org.springframework.stereotype.Component;
import ru.jabka.x6_order.model.ProductBasket;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductBasketMapper implements RowMapper<ProductBasket>{

    @Override
    public ProductBasket mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProductBasket.builder()
                .id(rs.getLong("id"))
                .basket_id(rs.getLong("id"))
                .product_id(rs.getLong("product_id"))
                .product_count(rs.getInt("product_count"))
                .position(rs.getInt("position"))
                .in_stock(rs.getInt("in_stock"))
                .build();
    }
}