package ru.jabka.x6_order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.jabka.x6_order.model.Basket;
import ru.jabka.x6_order.model.Order;
import ru.jabka.x6_order.model.ProductBasket;
import ru.jabka.x6_order.service.BasketService;
import ru.jabka.x6_order.service.OrderService;
import ru.jabka.x6_order.service.ProductBasketService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/order/")
@Tag(name = "Заказ")
public class OrderController {

    private final OrderService orderService;
    private final BasketService basketService;
    private final ProductBasketService productBasketService;

    public OrderController(final OrderService orderService,
                           final BasketService basketService,
                           final ProductBasketService productBasketService)
    {
        this.orderService = orderService;
        this.basketService = basketService;
        this.productBasketService = productBasketService;
    }

    @PostMapping
    @Operation(summary = "Создать заказ")
    public Order create(@RequestBody final Long user_id, @RequestBody final List<Long> products_id) {
        //создаем заказ
        Order order = Order.builder()
                .user_id(user_id)
                .date_create(LocalDate.now())
                .is_active(1)
                .draft(1)
                .total_sum(0D)
                .build();
        Order orderResult = orderService.create(order);
        //создаем корзину
        Basket basket = Basket.builder()
                .order_id(orderResult.getId())
                .build();
        basketService.create(basket);
        //добавляем продукты в корзину
        for (Long list: products_id) {
            ProductBasket productBasket = ProductBasket.builder()
                    .basket_id(basket.getId())
                    .product_id(list)
                    .product_count(1)
                    .position(1)
                    .in_stock(1)
                    .build();

            try {
                productBasketService.create(productBasket);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        return orderResult;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить заказ по id")
    public Order get(@PathVariable final Long id) {
        return orderService.getById(id);
    }
}