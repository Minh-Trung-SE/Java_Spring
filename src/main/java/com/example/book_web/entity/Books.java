package com.example.book_web.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Books {
    public String bookId;
    public String bookTitle;
    public int categoryId;
    public String linkPhoto;
    public int releaseYear;
    public String description;
    public String author;
    public long price;
    public int amount;

    @Override
    public String toString() {
        return "Books{" +
                "book_id=" + bookId +
                ", book_title='" + bookTitle + '\'' +
                ", category_id=" + categoryId +
                ", link_photo='" + linkPhoto + '\'' +
                ", release_year=" + releaseYear +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
