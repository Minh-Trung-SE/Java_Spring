package com.example.book_web.repository;

import com.example.book_web.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface UserRepository extends JpaRepository<Users, String>{
    Users getUsersByUserPhoneOrUserEmail(String userPhone, String userEmail);
    Users getUsersByUserPhone(String userPhone);
    Users getUsersByUserPhoneAndUserPassword(String userPhone, String userPassword);
}
