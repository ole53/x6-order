package ru.jabka.x6_order.repository.mapper;

import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;
import ru.jabka.x6_order.model.Basket;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BasketMapper implements RowMapper<Basket>{

    @Override
    public Basket mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Basket.builder()
                .id(rs.getLong("id"))
                .order_id(rs.getLong("order_id"))
                .build();
    }
}