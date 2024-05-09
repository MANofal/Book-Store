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

    private static final String CUSTOMER_BASE_URL = "http://localhost:9092/api/customers/";
    private static final String BOOK_BASE_URL = "http://localhost:9091/api/books/";
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate = new RestTemplate();


    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {

        ResponseEntity<Void> customerResponse = restTemplate
                .getForEntity(CUSTOMER_BASE_URL + order.getCustomerId(), Void.class);
        if (customerResponse.getStatusCode() != HttpStatus.OK) {
            return null;
        }

        try {
            restTemplate.exchange(
                    BOOK_BASE_URL + order.getBookId() + "/order/" + order.getQuantity(),
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
