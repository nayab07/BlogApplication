package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Comments;

public interface CommentRepo extends JpaRepository<Comments, Integer> {

}
