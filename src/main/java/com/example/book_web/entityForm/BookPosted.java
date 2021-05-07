package com.example.book_web.entityForm;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class BookPosted {
    public Timestamp timePosted;
    public String linkPhoto;
    public String bookTitle;
    public String releaseYear;
    public long price;
}
