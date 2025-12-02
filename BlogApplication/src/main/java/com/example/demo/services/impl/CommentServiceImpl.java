package com.example.demo.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Comments;
import com.example.demo.entity.Post;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payloads.CommentDto;
import com.example.demo.repository.CommentRepo;
import com.example.demo.repository.PostRepo;
import com.example.demo.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CommentDto createComments(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "postId", postId));
		Comments comments = this.modelMapper.map(commentDto, Comments.class);
		comments.setPost(post);
		Comments saveComments = this.commentRepo.save(comments);
		
		
		
		
		
		return this.modelMapper.map(saveComments, CommentDto.class);
	}

	@Override
	public void deleteComments(Integer commentId) {
		Comments comments = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comments", "commentId", commentId));
		this.commentRepo.delete(comments);

	}

}
