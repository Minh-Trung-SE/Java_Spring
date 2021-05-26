package com.example.book_web.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter @Setter
@Entity
@Table(name = "book_post")
public class BookPost {
    @Id
    @Column(name = "book_id")
    private int bookId;
    @Column(name = "user_phone")
    private String userPhone;
    @Column(name = "date_post")
    private Timestamp timestamp;

    public BookPost() {
    }
}
