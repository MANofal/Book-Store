package dev.training.microservices.service;


import dev.training.microservices.model.Order;
import dev.training.microservices.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BaseUrlService baseUrlService;

    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Void> customerResponse = restTemplate
                .getForEntity(baseUrlService.createBaseUrlForService("customer-service")
                        + "/api/customers/" + order.getCustomerId(), Void.class);
        if (customerResponse.getStatusCode() != HttpStatus.OK) {
            return null;
        }

        try {
            restTemplate.exchange(
                    baseUrlService.createBaseUrlForService("book-catalog-service")
                            + "/api/books/" + order.getBookId() + "/order/" + order.getQuantity(),
                    HttpMethod.PUT,
                    null,
                    Void.class
            );
        } catch (HttpClientErrorException e) {
            return null;
        }

        return orderRepository.save(order);
    }

    public Page<Order> findAll(PageRequest pageRequest) {
        return orderRepository.findAll(pageRequest);
    }

    public boolean delete(Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
