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

    public BookController() {
        books.add(new Book(id++, "Java", "Author 1", 20.0));
        books.add(new Book(id++, "Spring", "Author 2", 25.0));
        books.add(new Book(id++, "Spring Boot", "Author 3", 22.0));
    }

    // Get all books
    @GetMapping("/books")
    public List<Book> getBooks() {
        return books;
    }

    // Get book by id
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }

    // Create a new book
    @PostMapping("/books")
    public List<Book> createBook(@RequestBody Book book) {
        books.add(book);
        return books;
    }

    // Remove a book
    @DeleteMapping("/books/{id}")
    public List<Book> removeBook(@PathVariable Long id) {
        books.remove(this.getBook(id));
        return books;
    }

    /*
    // Update a book
    @PutMapping("/books/id")
    public List<Book> updateBook() {
        return books.stream().filter(book -> book.getId().equals(id));
    }


    // Partial update

    // Remove a book
    @DeleteMapping("/books/id")
    public List<Book> removeBook(@PathVariable Long id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i].getId().equals(id)) {
                books.remove();
            }
        }

        return books;
    }

    // Search by title
    @GetMapping("/books/search")
    public List<Book> searchByTitle(
            @RequestParam(required = false, defaultValue = "") String title
    ) {
        if (title.isEmpty()) {
            return books;
        }

        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Price range
    @GetMapping("/books/price-range")
    public List<Book> getBooksByPrice(
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice
    ) {
        return books.stream()
                .filter(book -> {
                    boolean min = minPrice == null || book.getPrice() >= minPrice;
                    boolean max = maxPrice == null || book.getPrice() <= maxPrice;

                    return min && max;
                }).collect(Collectors.toList());
    }


    // Sort
    @GetMapping("/books/sorted")
    public List<Book> getSortedBooks(
            @RequestParam(required = false, defaultValue = "title") String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String order
    ) {
        Comparator<Book> comparator;

        switch(sortBy.toLowerCase()) {
            case "author":
                comparator = Comparator.comparing(Book::getAuthor);
                break;
            default:
                comparator = Comparator.comparing(Book::getTitle);
                break;
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return books.stream().sorted(comparator)
                .collect(Collectors.toList());
    }
     */
}
