package com.example.book_web.repository;

import com.example.book_web.entity.BookPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPostRepository extends JpaRepository<BookPost, Integer> {
}
