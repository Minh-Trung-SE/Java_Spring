package com.example.book_web.entity;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class BookFavourite {
    String linkPhoto;
    String bookTitle;
    String yearRelease;
    String phoneContact;
    long price;

    @Override
    public String toString() {
        return "BookFavourite{" +
                "linkPhoto='" + linkPhoto + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", yearRelease='" + yearRelease + '\'' +
                ", phoneContact='" + phoneContact + '\'' +
                ", price=" + price +
                '}';
    }

}
