package dev.training.microservices.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String author;
    private Double price;
    private Integer quantity;

    public void update(Book bookUpdate) {
        if (bookUpdate.getTitle() != null) {
            this.title = bookUpdate.getTitle();
        }
        if (bookUpdate.getAuthor() != null) {
            this.author = bookUpdate.getAuthor();
        }
        if (bookUpdate.getPrice() != null) {
            this.price = bookUpdate.getPrice();
        }
        if (bookUpdate.getQuantity() != null) {
            this.quantity = bookUpdate.getQuantity();
        }
    }
}
