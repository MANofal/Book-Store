package dev.training.microservices.controller;


import dev.training.microservices.model.Customer;
import dev.training.microservices.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    private ResponseEntity<Customer> findById(@PathVariable Integer customerId) {
        Optional<Customer> optionalCustomer = customerService.findById(customerId);
        return optionalCustomer
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    private ResponseEntity<Void> createCustomer(@RequestBody Customer newCustomer, UriComponentsBuilder uriComponentsBuilder) {
        Customer savedCustomer = customerService.save(newCustomer);
        URI locationOfNewCustomer = uriComponentsBuilder
                .path("/api/customers/{customerId}")
                .buildAndExpand(savedCustomer.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCustomer).build();
    }

    @PutMapping("/{customerId}")
    private ResponseEntity<Void> updateCustomer(@PathVariable Integer customerId, @RequestBody Customer updatedCustomerInfo) {
        if (customerService.update(customerId, updatedCustomerInfo)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
