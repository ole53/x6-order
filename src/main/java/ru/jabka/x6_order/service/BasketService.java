package ru.jabka.x6_order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.jabka.x6_order.model.Basket;
import ru.jabka.x6_order.repository.BasketRepository;
import ru.jabka.x6_order.exception.BadRequestException;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;

    public Basket create(final Basket basket) {
        validate(basket);

        return basketRepository.insert(basket);
    }

    @Cacheable(value = "basket", key = "#id")
    public Basket getById(final Long id) {
        return basketRepository.getById(id);
    }

    @CachePut(value = "basket", key = "#basket.id")
    public Basket update(final Basket basket) {
        validate(basket);

        return basketRepository.update(basket);
    }

    private void validate(final Basket basket) {
        if (basket == null) {
            throw new BadRequestException("Корзина не может быть пустой!");
        }
        if (basket.getOrder_id() == null) {
            throw new BadRequestException("Невозможно создать корзину без заказа!");
        }
    }
}