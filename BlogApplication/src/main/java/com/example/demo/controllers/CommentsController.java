package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.CommentDto;
//import com.example.demo.payloads.PostDto;
import com.example.demo.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentsController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComments(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
		CommentDto comments = this.commentService.createComments(commentDto, postId);
		return new ResponseEntity<CommentDto>(comments,HttpStatus.OK); 
		
	}
	
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComments(@PathVariable Integer commentId){
		 this.commentService.deleteComments(commentId);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted successfully",true),HttpStatus.OK);
	}

}
