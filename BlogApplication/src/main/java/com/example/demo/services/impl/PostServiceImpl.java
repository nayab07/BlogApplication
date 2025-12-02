package com.example.demo.services.impl;


import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
//import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.server.ErrorPageRegistrarBeanPostProcessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payloads.PostDto;
import com.example.demo.payloads.PostResponse;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.userRepo;
import com.example.demo.services.PostService;


@Service
public class PostServiceImpl implements PostService {
	
	
	@Autowired
	private PostRepo postRepo;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private userRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;




	@Override
	public PostDto createPost(PostDto postDto ,Integer userId, Integer categoryId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "userId", userId));
		Category category =this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "category", categoryId));
		
		
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost=this.postRepo.save(post);
			
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto UpdatePost(PostDto postDto, Integer postId) {
		
	 Post post=	this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post ", "postId", postId));
	 post.setContent(postDto.getContent());
	 post.setTitle(postDto.getTitle());
	 
	 Post newPost=this.postRepo.save(post);
	 
	return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "postId", postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {

	   Sort sort=null;
	   
	   if(sortDir.equalsIgnoreCase("asc")) {
		   sort=Sort.by(sortBy).ascending();
	   }else {
		sort=Sort.by(sortBy).descending();
	}

	    // Step 1: Create Pageable object
	    
	    Pageable p = PageRequest.of(pageNumber, pageSize,sort);

	    // Step 2: Call findAll with pageable, NOT findAll()
	    Page<Post> pagePost = this.postRepo.findAll(p);

	    // Step 3: Extract only the actual content from Page object
	    List<Post> posts = pagePost.getContent();

	    // Step 4: Convert to DTO list
	    List<PostDto> postDtos = posts.stream()
	            .map(post -> this.modelMapper.map(post, PostDto.class))
	            .collect(Collectors.toList());
	    
	    
	    
	    PostResponse postResponse=new PostResponse();
	    postResponse.setContent(postDtos);
	    postResponse.setPageNumber(pagePost.getNumber());
	    postResponse.setPageSize(pagePost.getSize());
	    postResponse.setTotalElements(pagePost.getTotalElements());
	    postResponse.setTotalPage(pagePost.getTotalPages());
	    postResponse.setLastPage(pagePost.isLast());

	    // Step 5: Return final paginated DTO list
	    return postResponse;
	}


	@Override
	public PostDto getPostById(Integer postId) {
		
	  Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post ", "postId", postId));
	  
	   PostDto postDto=  this.modelMapper.map(post, PostDto.class);
		
		return postDto;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
	Category category=	this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "categoryId", categoryId));
    List<Post> posts=	this.postRepo.findByCategory(category);
   List<PostDto> listPostDtos=  posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return listPostDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
	
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "userId", userId));
		
		List<Post> posts=this.postRepo.findByUser(user);
		
		List<PostDto> listPostbyUser=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return listPostbyUser;
	}

	@Override
	public List<PostDto> SearchPost(String keywords) {
		
		List<Post> posts = this.postRepo.findByTitleContainingIgnoreCase(keywords);
		 List<PostDto> collect = posts.stream().map((post)->this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		return collect;
	}

}
