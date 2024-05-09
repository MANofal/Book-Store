package dev.training.microservices;

import dev.training.microservices.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import dev.training.microservices.model.Book;

@SpringBootApplication
public class BookApp {
    public static void main(String[] args) {
        SpringApplication.run(BookApp.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository){
        return (args) -> {
            bookRepository.save(new Book(null, "The Great Gatsby", "F. Scott Fitzgerald", 15.99, 10));
            bookRepository.save(new Book(null, "To Kill a Mockingbird", "Harper Lee", 12.50, 2));
            bookRepository.save(new Book(null, "1984", "George Orwell", 10.25, 5));
            bookRepository.save(new Book(null, "Pride and Prejudice", "Jane Austen", 14.75, 9));
        };
    }
}
