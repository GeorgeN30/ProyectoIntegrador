package com.proyecto.proyecto.service;

import com.proyecto.proyecto.exception.AuthorNotFoundException;
import com.proyecto.proyecto.exception.BookNotFoundException;
import com.proyecto.proyecto.exception.CategoryNotFoundException;
import com.proyecto.proyecto.mapper.BookMapper;
import com.proyecto.proyecto.model.dto.BookResponse;
import com.proyecto.proyecto.model.dto.CreateBookRequest;
import com.proyecto.proyecto.model.entity.Book;
import com.proyecto.proyecto.repository.AuthorRepository;
import com.proyecto.proyecto.repository.BookRepository;
import com.proyecto.proyecto.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResponse save(CreateBookRequest request) {
        return authorRepository.findById(request.getAuthorId())
                .map(author -> categoryRepository
                        .findById(request.getCategoryId()).
                        map( category -> {
                            Book book = new Book();
                            book.setTitle(request.getTitle());
                            book.setQuantity(request.getQuantity());
                            book.setAvailable(request.getAvailable());
                            book.setAuthor(author);
                            book.setCategory(category);
                            book.setDescription(request.getDescription());
                            return bookRepository.save(book);
                        })
                        .orElseThrow(CategoryNotFoundException::new))
                .map(bookMapper::toBookResponse)
                .orElseThrow(AuthorNotFoundException::new);
    }

    @Override
    public List<BookResponse> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBookResponse)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public BookResponse update(Long id, CreateBookRequest request) {
        return bookRepository.findById(id)
                .map(book -> authorRepository
                        .findById(request.getAuthorId())
                            .map(author -> categoryRepository
                                    .findById(request.getCategoryId())
                                    .map(category -> {
                                        book.setTitle(request.getTitle());
                                        book.setQuantity(request.getQuantity());
                                        book.setAvailable(request.getAvailable());
                                        book.setAuthor(author);
                                        book.setDescription(request.getDescription());
                                        book.setCategory(category);
                                        return bookRepository.save(book);
                                    })
                                    .orElseThrow(CategoryNotFoundException::new)
                            )
                            .orElseThrow(AuthorNotFoundException::new)
                    )
                .map(bookMapper::toBookResponse)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new BookNotFoundException();
        }
        bookRepository.deleteById(id);
    }
}
