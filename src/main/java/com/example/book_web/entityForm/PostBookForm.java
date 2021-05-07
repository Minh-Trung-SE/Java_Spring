package com.example.book_web.entityForm;
import com.example.book_web.entity.Books;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class PostBookForm {
    public String userPhone;
    public ArrayList<Books> listPostBook;
}
