package com.example.book_web.repository;

import com.example.book_web.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {
    ArrayList<Books> findAllByCategoryId(Integer categoryID);
    ArrayList<Books> findAllByBookTitle(String bookTitle);
}
