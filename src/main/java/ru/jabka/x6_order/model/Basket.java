package ru.jabka.x6_order.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Basket {

    private Long id;
    private Long order_id;
}