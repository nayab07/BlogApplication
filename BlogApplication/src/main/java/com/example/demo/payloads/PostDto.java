package com.example.demo.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.entity.Comments;

//import com.example.demo.entity.Comments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {
	
	private Integer postId;

    private String title;
	
	private String content;
	
	private String imageName;
	private Date addedDate;
	
	
	private CategoryDto category;
	

	private UserDto user;

	
	private Set<CommentDto> comment=new HashSet<>();
	

}
