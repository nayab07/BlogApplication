package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

import java.util.Optional;

public interface userRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

}
