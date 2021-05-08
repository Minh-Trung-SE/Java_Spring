package com.example.book_web.urlcontroler.responseModel;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class ResponseListBookPosted {
    public String bookTitle;
    public String linkPhoto;
    public String releaseYear;
    public long price;
    public Timestamp timePosted;
}
