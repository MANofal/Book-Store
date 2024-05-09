package dev.training.microservices.service;

import dev.training.microservices.model.Customer;
import dev.training.microservices.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    public Optional<Customer> findById(Integer customerId) { return customerRepository.findById(customerId); }

    public Customer save(Customer customer){ return customerRepository.save(customer); }

    public boolean update(Integer customerId, Customer updatedCustomerInfo){
        return customerRepository.findById(customerId)
                .map(existingCustomer -> {
                    existingCustomer.update(updatedCustomerInfo);
                    customerRepository.save(existingCustomer);
                    return true;
                })
                .orElse(false);
    }
}
