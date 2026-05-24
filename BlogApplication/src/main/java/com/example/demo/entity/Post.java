package com.example.demo.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name = "title" , length = 100,nullable = false)
	private String title;
	
	private String content;
	private String imageName;
	private Date addedDate;
	
	@ManyToOne
	private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
	
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private Set<Comments> comment=new HashSet<>();

	
}
