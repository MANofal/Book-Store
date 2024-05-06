package dev.training.microservices.controller;

import dev.training.microservices.model.Book;
import dev.training.microservices.service.BookService;
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
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{requestedId}")
    private ResponseEntity<Book> findById(@PathVariable Integer requestedId) {
        Optional<Book> optionalBook = bookService.findById(requestedId);
        return optionalBook
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    private ResponseEntity<Void> createBook(@RequestBody Book newBookRequest, UriComponentsBuilder uriComponentsBuilder) {
        Book savedBook = bookService.save(newBookRequest);
        URI locationOfNewBook = uriComponentsBuilder
                .path("/api/books/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewBook).build();
    }

    // http://localhost:9091/books?page=0&size=5
    @GetMapping
    private ResponseEntity<List<Book>> findAll(Pageable pageable) {
        Page<Book> page = bookService.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
                ));
        return ResponseEntity.ok(page.getContent());
    }

    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> updateBook(@PathVariable Integer requestedId, @RequestBody Book updatedBookInfo) {
        if (bookService.update(requestedId, updatedBookInfo)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        if (bookService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
