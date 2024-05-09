package dev.training.microservices.service;

import dev.training.microservices.model.Book;
import dev.training.microservices.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Page<Book> findAll(PageRequest pageRequest) {
        return bookRepository.findAll(pageRequest);
    }

    public boolean existsById(Integer id) {
        return bookRepository.existsById(id);
    }

    @Transactional
    public boolean update(Integer id, Book updatedBookInfo) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.update(updatedBookInfo);
                    bookRepository.save(existingBook);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public boolean orderBook(Integer id, Integer orderingQuantity) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    int remainingQuantity = existingBook.getQuantity() - orderingQuantity;
                    if (remainingQuantity < 0) {
                        return false;
                    }
                    existingBook.setQuantity(remainingQuantity);
                    bookRepository.save(existingBook);
                    return true;
                })
                .orElse(false);
    }


    @Transactional
    public boolean delete(Integer id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
