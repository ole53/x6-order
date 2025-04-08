package ru.jabka.x6_order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestTemplate restTemplate;

    public Boolean checkProduct(final Long product_id) {
        return restTemplate.getForObject("http://localhost:8081/api/v1/product/exist/{id}", Boolean.class, product_id);
    }
}