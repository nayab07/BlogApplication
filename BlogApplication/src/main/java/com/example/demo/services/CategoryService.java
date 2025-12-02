package com.example.demo.services;






import java.util.List;

import com.example.demo.payloads.CategoryDto;

public interface CategoryService {
	
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	public void deleteCategory(Integer categoryId);
	
	public CategoryDto getCategoryById(Integer categoryId);
	
	List<CategoryDto> getAllCategory();

}
