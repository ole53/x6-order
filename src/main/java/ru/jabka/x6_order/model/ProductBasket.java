package ru.jabka.x6_order.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductBasket {

    private Long id;
    private Long basket_id;
    private Long product_id;
    private Integer product_count;
    private Integer position;
    private Integer in_stock;
}
