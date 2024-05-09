package dev.training.microservices;

import dev.training.microservices.model.Customer;
import dev.training.microservices.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
        return (args) -> {
            customerRepository.save(new Customer(null,"John", "Doe", "john.doe@example.com", "1234567890"));
            customerRepository.save(new Customer(null,"Jane", "Smith", "jane.smith@example.com", "9876543210"));
            customerRepository.save(new Customer(null,"Michael", "Johnson", "michael.johnson@example.com", "5555555555"));

        };
    }

}
