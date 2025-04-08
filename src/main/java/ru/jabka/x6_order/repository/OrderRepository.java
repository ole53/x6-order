package ru.jabka.x6_order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.x6_order.model.Order;
import ru.jabka.x6_order.repository.mapper.OrderMapper;
import ru.jabka.x6_order.exception.BadRequestException;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final OrderMapper orderMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT = """
            INSERT INTO x6_application.ORDER (user_id, total_sum, date_create, draft)
            VALUES (:user_id, :total_sum, :date_create, :draft)
            RETURNING *;
            """;

    private static final String UPDATE = """
            UPDATE x6_application.ORDER o
            SET o.user_id = :user_id, o.total_sum = :total_sum,
                o.delivery_date = :delivery_date, o.draft = :draft, o.is_active = :is_active
            WHERE o.id = :id
            RETURNING *;
            """;

    private static final String GET_BY_ID = """
            SELECT * FROM x6_application.ORDER o
            WHERE o.id = :id;
            """;

    @Transactional(rollbackFor = Exception.class)
    public Order insert(final Order order) {
        return jdbcTemplate.queryForObject(INSERT, orderToSql(order), orderMapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public Order update(final Order order) {
        return jdbcTemplate.queryForObject(UPDATE, orderToSql(order), orderMapper);
    }

    @Transactional(readOnly = true)
    public Order getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), orderMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Заказ с id %d не найден", id));
        }
    }

    private MapSqlParameterSource orderToSql(final Order order) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", order.getId());
        params.addValue("user_id", order.getUser_id());
        params.addValue("total_sum", order.getTotal_sum());
        params.addValue("date_create", order.getDate_create());
        params.addValue("delivery_date", order.getDelivery_moment());
        params.addValue("draft", order.getDraft());
        params.addValue("is_active", order.getIs_active());

        return params;
    }
}