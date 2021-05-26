package com.example.book_web.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "book_favourite")
@Setter @Getter
public class BookFavourite {
    @Id
    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "book_id")
    private int bookId;

    @Column (name = "date_favourite")
    private Timestamp dateAdd;


    @Override
    public String toString() {
        return "BookFavourite{" +
                "userPhone='" + userPhone + '\'' +
                ", bookId=" + bookId +
                ", dateAdd=" + dateAdd +
                '}';
    }
}
