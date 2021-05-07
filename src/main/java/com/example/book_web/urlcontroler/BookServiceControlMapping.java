package com.example.book_web.urlcontroler;

import com.example.book_web.entity.BookFavourite;
import com.example.book_web.entity.Response;
import com.example.book_web.services.BookServices;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

@RestController
public class BookServiceControlMapping {
    private final BookServices bookServices;

    public BookServiceControlMapping(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @GetMapping(value = "/book/{id}")
    public Response showBookOrderBy(@PathVariable(value = "id") int id, @RequestParam(value = "orderBy", defaultValue = "book_title") String orderBy, @RequestParam(value = "order", defaultValue = "ASC") String order){
        /*If categoryId valid on database implement this logic and return response*/
        if(bookServices.isValidCategory(id)){
            return bookServices.getBookOrderBy(id, orderBy, order);
        }else {
            return new Response(-1, "Request failed!", null);
        }
    }

    @GetMapping(value = "/book/favourite/{userPhone}")
    public ArrayList<BookFavourite> showFavourite(@PathVariable(value = "userPhone") String userPhone, @RequestParam(value = "orderBy", defaultValue = "book_title") String orderBy, @RequestParam(value = "order", defaultValue = "ASC") String order){
        System.out.println("OK");
        return bookServices.getFavoriteBook(userPhone, orderBy, order);
    }

    @GetMapping (value = "/book/dis-favourite/")
    public String disLike(@RequestParam(name = "userPhone") String userPhone, @RequestParam(name = "bookId") String bookId){
        return bookServices.disLikeBook(userPhone, bookId);
    }

    @GetMapping (value = "/book/add-favourite/")
    public String like(@RequestParam(name = "userPhone") String userPhone, @RequestParam(name = "bookId") String bookId){
        return bookServices.addLikeBook(userPhone, bookId);
    }

}
