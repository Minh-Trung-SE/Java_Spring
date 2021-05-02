package com.example.book_web;

import com.example.book_web.model.BookServices;
import com.example.book_web.model.UserServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookWebApplication.class, args);
		BookServices bookServices = new BookServices();
		bookServices.getFavoriteBook("0335840115");
	}

}
