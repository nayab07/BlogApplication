package com.example.demo.controllers;

import java.util.List;


import com.example.demo.payloads.LoginDto;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.UserDto;
import com.example.demo.services.userService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private userService userService;


	
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createUserDto=this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){
		UserDto updateUser=this.userService.updateUser(userDto, userId);
		return  ResponseEntity.ok(updateUser);
		
	}

    @PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser( @PathVariable Integer userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse> (new ApiResponse("User deleted",true),HttpStatus.OK);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser() {
	    List<UserDto> users = this.userService.getAllUsers();
	    return ResponseEntity.ok(users);
	}

	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> singleUser(@PathVariable Integer userId){
		UserDto userDto= this.userService.getUserById(userId);
		return ResponseEntity.ok(userDto);
	}


	
	
	
	
	
	
	
	
	
	
	


}
