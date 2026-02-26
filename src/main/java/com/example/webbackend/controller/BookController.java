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

    // Filter by price
    private List<Book> filterByPrice(List<Book> entries, Double minPrice, Double maxPrice) {
        if (maxPrice < 0.0 && minPrice < 0.0) {
            return entries;
        }
        else if (maxPrice < 0.0) {
            return entries.stream().
                    filter(book -> book.getPrice() >= minPrice)
                    .collect(Collectors.toList());
        }
        else if (minPrice < 0.0) {
            return entries.stream().
                    filter(book -> book.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }
        return entries.stream().
                filter(book -> book.getPrice() >= minPrice && book.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // Sort books
    private List<Book> sort(List<Book> entries, String sortBy, String order) {
        Comparator<Book> comparator;
        switch (sortBy.toLowerCase()) {
            case "title" -> comparator = Comparator.comparing(Book::getTitle);
            case "author" -> comparator = Comparator.comparing(Book::getAuthor);
            case "price" -> comparator = Comparator.comparing(Book::getPrice);
            default -> comparator = Comparator.comparing(Book::getId);
        }

        if (order.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        return entries.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    // Pagination
    private List<Book> paginate(List<Book> entries, Integer page, Integer pageSize) {
        if (pageSize <= 0) {
            return entries;
        }

        int skip = page * pageSize;
        return entries.stream()
                .skip(skip)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public BookController() {
        books.add(new Book(id++, "The Alchemist", "Paulo Coelho", 48.0));
        books.add(new Book(id++, "Rebecca", "Daphne Du Maurier", 37.0));
        books.add(new Book(id++, "Les Miserables", "Victor Hugo", 22.0));
        books.add(new Book(id++, "The Autobiography of Malcolm X", "Malcolm X", 65.0));
        books.add(new Book(id++, "Frankenstein", "Mary Shelley", 26.0));
        books.add(new Book(id++, "Murder on the Orient Express", "Agatha Christie", 13.0));
    }

    // Get all books
    @GetMapping("/books")
    public List<Book> getBooks(
            @RequestParam(required = false, defaultValue = "-1.0") Double minPrice,
            @RequestParam(required = false, defaultValue = "-1.0") Double maxPrice,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "0") Integer pageSize
    ) {
        List<Book> result = books;

        result = filterByPrice(result, minPrice, maxPrice);
        result = sort(result, sortBy, order);
        result = paginate(result, page, pageSize);

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
}
