package ru.jabka.x6_order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;

    public Boolean checkUser(final Long user_id) {
        return restTemplate.getForObject("http://localhost:8081/api/v1/user/exist/{id}", Boolean.class, user_id);
    }
}