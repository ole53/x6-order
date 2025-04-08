package ru.jabka.x6_order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.x6_order.repository.mapper.BasketMapper;
import ru.jabka.x6_order.model.Basket;
import ru.jabka.x6_order.exception.BadRequestException;

@Repository
@RequiredArgsConstructor
public class BasketRepository {

    private final BasketMapper basketMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT = """
            INSERT INTO x6_application.BASKET (order_id)
            VALUES (:order_id)
            RETURNING *;
            """;

    private static final String UPDATE = """
            UPDATE x6_application.BASKET b
            SET b.order_id = :order_id
            WHERE b.id = :id
            RETURNING *;
            """;

    private static final String GET_BY_ID = """
            SELECT * FROM x6_application.BASKET b
            WHERE b.id = :id;
            """;

    @Transactional(rollbackFor = Exception.class)
    public Basket insert(final Basket basket) {
        return jdbcTemplate.queryForObject(INSERT, basketToSql(basket), basketMapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public Basket update(final Basket basket) {
        return jdbcTemplate.queryForObject(UPDATE, basketToSql(basket), basketMapper);
    }

    @Transactional(readOnly = true)
    public Basket getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), basketMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Корзина с id %d не найдена", id));
        }
    }

    private MapSqlParameterSource basketToSql(final Basket basket) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", basket.getId());
        params.addValue("order_id", basket.getOrder_id());

        return params;
    }
}