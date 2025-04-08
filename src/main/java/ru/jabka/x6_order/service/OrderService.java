package ru.jabka.x6_order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.jabka.x6_order.model.Order;
import ru.jabka.x6_order.repository.OrderRepository;
import ru.jabka.x6_order.exception.BadRequestException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public Order create(final Order order) {
        validate(order);

        return orderRepository.insert(order);
    }

    @Cacheable(value = "order", key = "#id")
    public Order getById(final Long id) {
        return orderRepository.getById(id);
    }

    @CachePut(value = "order", key = "#order.id")
    public Order update(final Order order) {
        validate(order);

        return orderRepository.update(order);
    }

    private void validate(final Order order) {
        if (order == null) {
            throw new BadRequestException("Введите информацию о заказе");
        }
        if (order.getUser_id() == null) {
            throw new BadRequestException("Укажите id пользователя!");
        }
        if (!userService.checkUser(order.getUser_id())) {
            throw new BadRequestException("Указанный пользователь не найден!");
        }
    }
}