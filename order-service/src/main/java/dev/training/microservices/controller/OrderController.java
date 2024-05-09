package dev.training.microservices.controller;


import dev.training.microservices.model.Order;
import dev.training.microservices.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{requestedId}")
    private ResponseEntity<Order> findById(@PathVariable Integer requestedId) {
        Optional<Order> optionalOrder = orderService.findById(requestedId);
        return optionalOrder
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    private ResponseEntity<Void> createOrder(@RequestBody Order newOrderRequest, UriComponentsBuilder uriComponentsBuilder) {
        Order savedBook = orderService.createOrder(newOrderRequest);
        URI locationOfNewOrder = uriComponentsBuilder
                .path("/api/orders/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewOrder).build();
    }


    @GetMapping
    private ResponseEntity<List<Order>> findAll(Pageable pageable) {
        Page<Order> page = orderService.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
                ));
        return ResponseEntity.ok(page.getContent());
    }



    @DeleteMapping("/{id}")
    private ResponseEntity<Void> cancelOrder(@PathVariable Integer id) {
        if (orderService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
