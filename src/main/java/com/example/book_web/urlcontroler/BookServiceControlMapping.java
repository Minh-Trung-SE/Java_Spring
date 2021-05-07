package com.example.book_web.urlcontroler;

import com.example.book_web.entity.BookFavourite;
import com.example.book_web.entityForm.BookPosted;
import com.example.book_web.entityForm.PostBookForm;
import com.example.book_web.entityForm.Response;
import com.example.book_web.services.BookServices;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class BookServiceControlMapping {
    private final BookServices bookServices;

    public BookServiceControlMapping(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @GetMapping(value = "/book/")
    public Response showBookOrderBy(@RequestParam(value = "bookId") int bookId, @RequestParam(value = "orderBy", defaultValue = "book_title") String orderBy, @RequestParam(value = "order", defaultValue = "ASC") String order){
        /*If categoryId valid on database implement this logic and return response*/
        if(bookServices.isValidCategory(bookId)){
            return bookServices.getBookOrderBy(bookId, orderBy, order);
        }else {
            return new Response(-1, "Request failed!", null);
        }
    }

    @GetMapping(value = "/book/favourite")
    public ArrayList<BookFavourite> showFavourite(@RequestParam(name = "userPhone") String userPhone, @RequestParam(value = "orderBy", defaultValue = "book_title") String orderBy, @RequestParam(value = "order", defaultValue = "ASC") String order){
        return bookServices.getFavoriteBook(userPhone, orderBy, order);
    }

    @GetMapping (value = "/book/dis-favourite")
    public String disLike(@RequestParam(name = "userPhone") String userPhone, @RequestParam(name = "bookId") String bookId){
        return bookServices.disLikeBook(userPhone, bookId);
    }

    @GetMapping (value = "/book/add-favourite")
    public String like(@RequestParam(name = "userPhone") String userPhone, @RequestParam(name = "bookId") String bookId){
        return bookServices.addLikeBook(userPhone, bookId);
    }

    @PostMapping(value = "/book/post-book")
    public  String postBook(@RequestBody PostBookForm bookForm){
        return bookServices.postBook(bookForm);
    }

}
