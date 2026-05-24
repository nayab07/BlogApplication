package com.example.demo.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payloads.CategoryDto;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.services.CategoryService;


@Service
public class CategoryImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
	    
		Category cat=this.modelMapper.map(categoryDto, Category.class);
		Category saveCategory=this.categoryRepo.save(cat);
		return this.modelMapper.map(saveCategory, CategoryDto.class);
		
		
		
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category category= this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		
		Category updateCategory=this.categoryRepo.save(category);
	
		return this.modelMapper.map(updateCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
	Category category=	this.categoryRepo.findById(categoryId)
		       .orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
	
	this.categoryRepo.delete(category);

	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
	   List<Category> categories =this.categoryRepo.findAll();
	   List<CategoryDto> category= categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return category;
	}

}
