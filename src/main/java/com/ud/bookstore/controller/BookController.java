package com.ud.bookstore.controller;

import com.ud.bookstore.dto.BookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/api/v1/books")
@Tag(name = "Book List", description = "APIs for managing books")
public class BookController {

    private static List<BookDTO> bookList;

    public BookController() {
        log.info("BookController(): initialising the List of books");
        bookList = new ArrayList<>(Arrays.asList(
                BookDTO.builder()
                        .bookId("1")
                        .bookName("Java Essentials")
                        .bookAuthor("Udith")
                        .description("Java Essentials")
                        .price("100")
                        .build(),
                BookDTO.builder()
                        .bookId("2")
                        .bookName("MongoDB 101")
                        .bookAuthor("Nuwan")
                        .description("MongoDB 101")
                        .price("200")
                        .build(),
                BookDTO.builder()
                        .bookId("3")
                        .bookName("Python for Dummies")
                        .bookAuthor("DV")
                        .description("Python for Dummies")
                        .price("300")
                        .build()));
    }

    @Operation(summary = "Get book by ID", description = "Retrieve a specific book by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBook(@PathVariable String bookId) {
        log.info("BookController(): getting book by ID {}", bookId);
        BookDTO bookDto = bookList.stream()
                .filter(b -> b.getBookId().equals(bookId)).findFirst().orElse(null);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @Operation(summary = "Get all books", description = "Retrieve all the books in the List ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "404", description = "Books not found", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        log.info("BookController(): getting all books");
        return ResponseEntity.ok(bookList);
    }

    @Operation(summary = "Add book details", description = "add new book in the List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book added successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<String> addBook(@RequestBody() @Valid BookDTO book) {
        log.info("BookController(): adding book {}", book);
        bookList.add(book);
        return ResponseEntity.ok("New Book added with BookID: " +book.getBookId());
    }

    @Operation(summary = "Update  book details", description = "update book in the List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/")
    public ResponseEntity<String> updateBook(@RequestBody() @Valid BookDTO book) {
        log.info("BookController(): updating book {}", book);
        bookList.stream()
                .filter(b -> b.getBookId().equals(book.getBookId()))
                .forEach(b -> {
                    b.setBookName(book.getBookName());
                    b.setBookAuthor(book.getBookAuthor());
                    b.setDescription(book.getDescription());
                    b.setPrice(book.getPrice());
                });

        return ResponseEntity.ok("Book updated.");
    }

    @Operation(summary = "Delete book by ID", description = "Remove a book from the list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "book not found", content = @Content)
    })
    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable String bookId) {
        log.info("BookController(): deleting book {}", bookId);
        bookList.removeIf(b -> b.getBookId().equals(bookId));
        return ResponseEntity.ok("Book deleted");
    }
}
