package com.example.book_web.repository;

import com.example.book_web.entity.BookCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategories, Integer> {
      BookCategories findBookCategoriesByCategoryId(Integer categoryId);
}
