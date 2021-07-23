package com.example.book_web.repository;

import com.example.book_web.entity.BookFavourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BookFavouriteRepository extends JpaRepository<BookFavourite, String> {
    BookFavourite findByUserPhoneAndBookId(String userPhone, int bookId);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM `book_favourite` WHERE (`user_phone` =?1) AND (`book_id` =?2);")
    void disFavourite(String userPhone, int bookId);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO `book_favourite` (`user_phone`, `book_id`) VALUES (?1, ?2);")
    void addFavouriteBook(String userPhone, int bookId);
}
