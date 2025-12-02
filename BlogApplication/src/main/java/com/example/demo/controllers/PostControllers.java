package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.PostDto;
import com.example.demo.payloads.PostResponse;
import com.example.demo.services.PostService;


@RestController
@RequestMapping("/api/")
public class PostControllers {
	
	@Autowired
	private PostService postService;
	
	 
	// create
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId)
	
	{
		
		PostDto createPost=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	
	//get post by userId
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> findByuserPost(@PathVariable Integer userId)
	{
		List<PostDto> post=this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(post,HttpStatus.OK);
		
	}
	
	// get post by category
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> findByCategoryPost(@PathVariable Integer categoryId)
	{
		List<PostDto> posts=this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	// update Post
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId)
	{
		PostDto updatePost=this.postService.UpdatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		
	}
	
	
	// get all post 
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllpost(@RequestParam( defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(defaultValue = "3",required = false) Integer pageSize,
			@RequestParam(defaultValue = "postId", required = false) String sortBy,
			@RequestParam(defaultValue = "asc",required = false)String sortDir
			
			)
	{
		PostResponse getAllPost=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(getAllPost,HttpStatus.OK);
		
	}
	
	// get post by id
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
		PostDto getPost=this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(getPost,HttpStatus.OK);
		
	}
	
	// delete posts
	
	@DeleteMapping("/post/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) 
	{
		this.postService.deletePost(postId);
		return new ApiResponse("Post successfully deleted !!",true);
		
	}
	
	// search post 
	
	@GetMapping("/post/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPost(
			@PathVariable String keywords
			)
	{
	 List<PostDto> post  = this.postService.SearchPost(keywords);
	 return new ResponseEntity<List<PostDto>>(post,HttpStatus.OK);
	}
	
	
	
	
	
	
	

}
