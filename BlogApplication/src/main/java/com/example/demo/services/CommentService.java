package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.payloads.CommentDto;

@Service
public interface CommentService {
	public CommentDto createComments(CommentDto commentDto,Integer postId);
	public void deleteComments(Integer commentId);

}
