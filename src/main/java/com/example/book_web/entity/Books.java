package com.example.book_web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book_storage")
@Getter @Setter
public class Books {
    @Id
    @Column(name = "book_id")
    private int bookId;
    @Column(name = "book_title")
    private String bookTitle;
    @Column(name = "category_id")
    private int categoryId;
    @Column(name = "link_photo")
    private String linkPhoto;
    @Column(name = "release_year")
    private int releaseYear;
    @Column(name = "description")
    private String description;
    @Column(name = "author")
    private String author;
    @Column(name =  "price")
    private long price;
    @Column(name = "amount")
    private int amount;

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
