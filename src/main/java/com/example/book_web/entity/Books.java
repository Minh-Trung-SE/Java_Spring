package com.example.book_web.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Books {
    public String book_id;
    public String book_title;
    public int category_id;
    public String link_photo;
    public int release_year;
    public String description;
    public String author;
    public long price;
    public int amount;

    @Override
    public String toString() {
        return "Books{" +
                "book_id=" + book_id +
                ", book_title='" + book_title + '\'' +
                ", category_id=" + category_id +
                ", link_photo='" + link_photo + '\'' +
                ", release_year=" + release_year +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
