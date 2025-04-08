package ru.jabka.x6_order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.x6_order.model.ProductBasket;
import ru.jabka.x6_order.repository.mapper.ProductBasketMapper;
import ru.jabka.x6_order.exception.BadRequestException;

@Repository
@RequiredArgsConstructor
public class ProductBasketRepository {

    private final ProductBasketMapper productBasketMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT = """
            INSERT INTO x6_application.product_in_basket (product_id, basket_id, product_count, position, in_stock)
            VALUES (:product_id, :basket_id, :product_count, :position, :in_stock)
            RETURNING *;
            """;

    private static final String UPDATE = """
            UPDATE x6_application.product_in_basket p
            SET p.product_id = :product_id, p.basket_id = :basket_id, p.product_count = :product_count,
                p.position = :position, p.in_stock = :in_stock
            WHERE p.id = :id
            RETURNING *;
            """;

    private static final String GET_BY_ID = """
            SELECT * FROM x6_application.product_in_basket p
            WHERE p.id = :id;
            """;

    @Transactional(rollbackFor = Exception.class)
    public ProductBasket insert(final ProductBasket productBasket) {
        return jdbcTemplate.queryForObject(INSERT, productBasketToSql(productBasket), productBasketMapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductBasket update(final ProductBasket productBasket) {
        return jdbcTemplate.queryForObject(UPDATE, productBasketToSql(productBasket), productBasketMapper);
    }

    @Transactional(readOnly = true)
    public ProductBasket getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), productBasketMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Запись позиции корзины с id %d не найдена", id));
        }
    }

    private MapSqlParameterSource productBasketToSql(final ProductBasket productBasket) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", productBasket.getId());
        params.addValue("product_id", productBasket.getProduct_id());
        params.addValue("basket_id", productBasket.getProduct_id());
        params.addValue("product_count", productBasket.getProduct_count());
        params.addValue("position", productBasket.getPosition());
        params.addValue("in_stock", productBasket.getIn_stock());

        return params;
    }
}