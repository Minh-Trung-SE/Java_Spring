package com.example.book_web.urlcontroler;

import com.example.book_web.urlcontroler.responseModel.ResponseListBookFavourite;
import com.example.book_web.urlcontroler.requestModel.PostBookForm;
import com.example.book_web.urlcontroler.responseModel.ResponseListBookOrderByCategory;
import com.example.book_web.services.BookServices;
import com.example.book_web.urlcontroler.responseModel.ResponseListBookPosted;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class BookServiceControlMapping {
    private final BookServices bookServices;

    public BookServiceControlMapping(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @GetMapping(value = "/book/")
    public ResponseListBookOrderByCategory showBookOrderBy(@RequestParam(value = "bookId") int bookId, @RequestParam(value = "orderBy", defaultValue = "book_title") String orderBy, @RequestParam(value = "order", defaultValue = "ASC") String order){
        /*If categoryId valid on database implement this logic and return response*/
        if(bookServices.isValidCategory(bookId)){
            return bookServices.getBookOrderBy(bookId, orderBy, order);
        }else {
            return new ResponseListBookOrderByCategory(-1, "Request failed!", null);
        }
    }

    @GetMapping(value = "/book/favourite")
    public ArrayList<ResponseListBookFavourite> showFavourite(@RequestParam(name = "userPhone") String userPhone, @RequestParam(value = "orderBy", defaultValue = "book_title") String orderBy, @RequestParam(value = "order", defaultValue = "ASC") String order){
        return bookServices.getFavoriteBook(userPhone, orderBy, order);
    }

    @DeleteMapping (value = "/book/dis-favourite")
    public String disLike(@RequestParam(name = "userPhone") String userPhone, @RequestParam(name = "bookId") Integer bookId){
        return bookServices.disFavouriteBook(userPhone, bookId);
    }

    @GetMapping (value = "/book/add-favourite")
    public String like(@RequestParam(name = "userPhone") String userPhone, @RequestParam(name = "bookId") int bookId){
        return bookServices.addFavouriteBook(userPhone, bookId);
    }

    @PostMapping(value = "/book/post-book")
    public  String postBook(@RequestBody PostBookForm bookForm){
        return bookServices.postBook(bookForm);
    }

    @GetMapping(value ="/book/show-post-book" )
    public ArrayList<ResponseListBookPosted> showPostBook(@RequestParam (name = "userPhone") String userPhone, @RequestParam(value = "order", defaultValue = "ASC") String order, @RequestParam(value = "orderBy", defaultValue = "book_title") String orderBy){
        return bookServices.showBookPosted(userPhone, orderBy, order);
    }

}
