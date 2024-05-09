package dev.training.microservices.service;


import dev.training.microservices.model.Order;
import dev.training.microservices.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {

//        Long customerId;
//        Long bookId;
//        Integer quantity;

        restTemplate
                .getForEntity("/api/books/" + order.getBookId(), String.class);
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
