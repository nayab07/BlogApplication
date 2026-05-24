package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Post;
import com.example.demo.payloads.PostDto;
import com.example.demo.payloads.PostResponse;

@Service
public interface PostService {
	
	// create
	
PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);

//update

PostDto UpdatePost(PostDto postDto,Integer postId);

// delete

void deletePost(Integer postId);

// getAll post

PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

//getSingle Post

PostDto getPostById(Integer postId);

// get all post by category

List<PostDto> getPostByCategory(Integer categoryId);

// get all post by user

List<PostDto> getPostByUser(Integer userId);

// search Post

//List<PostDto> SearchPost(String keywords, int page, int size);


    Page<PostDto> searchPosts(String keyword, int page, int size);
}
