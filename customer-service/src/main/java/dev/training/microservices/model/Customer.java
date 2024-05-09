package dev.training.microservices.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public void update(Customer customerUpdate){
        if (customerUpdate.getFirstName() != null) {
            this.firstName = customerUpdate.getFirstName();
        }
        if (customerUpdate.getLastName() != null) {
            this.lastName = customerUpdate.getLastName();
        }
        if (customerUpdate.getEmail() != null) {
            this.email = customerUpdate.getEmail();
        }
        if (customerUpdate.getPhoneNumber() != null) {
            this.phoneNumber = customerUpdate.getPhoneNumber();
        }
    }




}
