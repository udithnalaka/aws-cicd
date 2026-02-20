package com.ud.bookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookDTO {

    @NotEmpty(message = "Book ID  required")
    private String bookId;

    @NotBlank(message = "Book name required")
    @Size(min = 5)
    @Schema(description = "Name of book")
    private String bookName;

    @NotBlank
    @Schema(description = "Author/s of the book")
    private String bookAuthor;

    @Schema(description = "total price of the book")
    String price;

    @Size(max = 50)
    @Schema(description = "describe the book content")
    String description;
}
