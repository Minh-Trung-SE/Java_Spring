package com.example.book_web.urlcontroler;

import com.example.book_web.entity.Response;
import com.example.book_web.model.Book_Services;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class BookServiceControlMapping {
    Book_Services book_services = new Book_Services();
    @GetMapping(value = "/book/{id}")
    public Response showBookOrderBy(@PathVariable(value = "id") int id, @PathParam("orderBy") String orderBy, @PathParam("order") String order){

        if(book_services.isValidCategory(id, book_services.getCategories())){
            System.out.println("Request status: 200");
            return book_services.getBookOrderBy(id, 200, orderBy, order);
        }else {
            System.out.println("Request failed!");
        }
        return book_services.getBookOrderBy(id, -1, orderBy, order);
    }
}
