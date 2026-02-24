package com.example.webbackend.controller;

import com.example.webbackend.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookController {
    // Get all books
    private List<Book> books = new ArrayList<>();
    private Long id = 1L;

    private List<Book> paginate(List<Book> entries, Integer page, Integer pageSize) {
        int skip = page * pageSize;

        return entries.stream()
                .skip(skip)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public BookController() {
        books.add(new Book(id++, "Java", "Author 1", 20.0));
        books.add(new Book(id++, "Spring", "Author 2", 25.0));
        books.add(new Book(id++, "Spring Boot", "Author 3", 22.0));
    }

    // Get all books
    @GetMapping("/books")
    public List<Book> getBooks(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "0") Integer pageSize
    ) {
        List<Book> result = books;
        if (pageSize > 0) {
            result = paginate(result, page, pageSize);
        }
        return result;
    }

    // Get book by id
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Create a new book
    @PostMapping("/books")
    public Book createBook(@RequestBody Book book) {
        books.add(book);
        return book;
    }

    // Update a book
    @PutMapping("/books/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        books = books.stream()
                .map(b -> b.getId().equals(id) ? book : b)
                .collect(Collectors.toList());
        return getBook(id);
    }

    // Partially update a book
    @PatchMapping("/books/{id}")
    public Book partialUpdateBook(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "") String author,
            @RequestParam(required = false, defaultValue = "-1.0") Double price
    ) {
        Book book = getBook(id);
        if (title.isEmpty()) {
            title = book.getTitle();
        }
        if (author.isEmpty()) {
            author = book.getAuthor();
        }
        if (price == -1.0) {
            price = book.getPrice();
        }

        book = new Book(id, title, author, price);
        return updateBook(book, id);
    }

    // Remove a book
    @DeleteMapping("/books/{id}")
    public List<Book> removeBook(@PathVariable Long id) {
        books.remove(getBook(id));
        return books;
    }

    // Get all books sorted
    @GetMapping("/books/sorted")
    public List<Book> getSortedBooks(
            @RequestParam(required = false, defaultValue = "title") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order
    ) {
        Comparator<Book> comparator;

        if (sortBy.equalsIgnoreCase("author")) {
            comparator = Comparator.comparing(Book::getAuthor);
        }
        else {
            comparator = Comparator.comparing(Book::getTitle);
        }

        if (order.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        return books.stream().sorted(comparator)
                .collect(Collectors.toList());
    }
}
