package dev.training.microservices;

import dev.training.microservices.model.Order;
import dev.training.microservices.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(OrderRepository customerRepository){
        return (args) -> {
            customerRepository.save(new Order(null, 1L, 1L, 2));
            customerRepository.save(new Order(null, 1L, 1L, 2));
            customerRepository.save(new Order(null, 1L, 1L, 2));
        };
    }

}
