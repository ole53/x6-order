package ru.jabka.x6_order.repository.mapper;

import org.springframework.stereotype.Component;
import ru.jabka.x6_order.model.Order;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Order.builder()
                .id(rs.getLong("id"))
                .user_id(rs.getLong("user_id"))
                .total_sum(rs.getDouble("total_sum"))
                .date_create(rs.getObject("date_create", LocalDate.class))
                .delivery_moment(rs.getObject("delivery_moment", LocalDate.class))
                .draft(rs.getInt("draft"))
                .is_active(rs.getInt("is_active"))
                .build();
    }
}