package com.example.demo.services;



import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.payloads.UserDto;

@Service
public interface userService {
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	
	
	

}
