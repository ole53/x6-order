package ru.jabka.x6_order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.jabka.x6_order.exception.BadRequestException;
import ru.jabka.x6_order.model.ProductBasket;
import ru.jabka.x6_order.repository.ProductBasketRepository;

@Service
@RequiredArgsConstructor
public class ProductBasketService {

    private final ProductBasketRepository productBasketRepository;
    private final ProductService productService;

    public ProductBasket create(final ProductBasket productBasket) {
        validate(productBasket);

        return productBasketRepository.insert(productBasket);
    }

    @Cacheable(value = "product-basket", key = "#id")
    public ProductBasket getById(final Long id) {
        return productBasketRepository.getById(id);
    }

    @CachePut(value = "product-basket", key = "#productBasket.id")
    public ProductBasket update(final ProductBasket productBasket) {
        validate(productBasket);

        return productBasketRepository.update(productBasket);
    }

    private void validate(final ProductBasket productBasket) {
        if (productBasket == null) {
            throw new BadRequestException("Позиция корзины не может быть пустой!");
        }
        if (productBasket.getBasket_id() == null) {
            throw new BadRequestException("Необходимо указать id корзины!");
        }
        if (productBasket.getProduct_id() == null) {
            throw new BadRequestException("Необходимо указать id продукта!");
        }
        if (!productService.checkProduct(productBasket.getProduct_id())) {
            throw new BadRequestException("По указанному id не найден продукт!");
        }
        if (productBasket.getProduct_count() == 0) {
            throw new BadRequestException("Количество продукта не может быть равно нолю!");
        }
    }
}