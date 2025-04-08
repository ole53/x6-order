package ru.jabka.x6_order.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Order {

    private Long id;
    private Long user_id;
    private Double total_sum;
    private LocalDate date_create;
    private LocalDate delivery_moment;
    private Integer draft;
    private Integer is_active;
}