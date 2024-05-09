package dev.training.microservices;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import dev.training.microservices.model.Book;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookBookAppTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void Should_Return_A_Book_When_Data_Is_Saved() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/api/books/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(1);

        String title = documentContext.read("$.title");
        assertThat(title).isEqualTo("The Great Gatsby");

        String author = documentContext.read("$.author");
        assertThat(author).isEqualTo("F. Scott Fitzgerald");

        Double amount = documentContext.read("$.price");
        assertThat(amount).isEqualTo(15.99);

        Number quantity = documentContext.read("$.quantity");
        assertThat(quantity).isEqualTo(10);
    }

    @Test
    @DirtiesContext
    void Should_Create_A_New_Book() {
        Book newBook = new Book(null, "To Kill a Mockingbird", "Harper Lee", 12.50, 5);
        ResponseEntity<Void> createResponse = restTemplate
                .postForEntity("/api/books", newBook, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfBook = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity(locationOfBook, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isNotNull();

        String title = documentContext.read("$.title");
        assertThat(title).isEqualTo("To Kill a Mockingbird");

        String author = documentContext.read("$.author");
        assertThat(author).isEqualTo("Harper Lee");

        Double amount = documentContext.read("$.price");
        assertThat(amount).isEqualTo(12.50);

        Number quantity = documentContext.read("$.quantity");
        assertThat(quantity).isEqualTo(5);
    }

    @Test
    void Should_Return_A_Page_Of_Books() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/api/books?page=0&size=3", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(3);
    }

    @Test
    @DirtiesContext
    void Should_Update_A_Book() {
        Book updatedBookInfo = new Book(null, null, "Nofal", null, 5);
        HttpEntity<Book> request = new HttpEntity<>(updatedBookInfo);
        ResponseEntity<Void> response = restTemplate
                .exchange("/api/books/1", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/books/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(1);

        String title = documentContext.read("$.title");
        assertThat(title).isEqualTo("The Great Gatsby");

        String author = documentContext.read("$.author");
        assertThat(author).isEqualTo("Nofal");

        Double amount = documentContext.read("$.price");
        assertThat(amount).isEqualTo(15.99);

        Number quantity = documentContext.read("$.quantity");
        assertThat(quantity).isEqualTo(5);
    }

    @Test
    @DirtiesContext
    void Should_Delete_A_Book() {
        ResponseEntity<Void> response = restTemplate
                .exchange("/api/books/1",
                        HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/books/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}