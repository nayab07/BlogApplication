package com.example.demo.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payloads.UserDto;
import com.example.demo.repository.userRepo;
import com.example.demo.services.userService;

@Service
public class UserServiceImpl implements userService {

    @Autowired
    private userRepo userRepo;
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;



    // ✅ Create User
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepo.findById(2).get();

        user.getRoles().add(role);
        User savedUser = this.userRepo.save(user);
        return this.userToUserDto(savedUser);
    }

    // ✅ Update User
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());

        User updatedUser = this.userRepo.save(user);
        return this.userToUserDto(updatedUser);
    }

    // ✅ Get Single User by ID
    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return this.userToUserDto(user);
    }

    // ✅ Get All Users
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream()
                .map(this::userToUserDto)
                .collect(Collectors.toList());
        return userDtos;
    }

    // ✅ Delete User
    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }

    // ✅ Convert DTO to Entity
    private User dtoToUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    // ✅ Convert Entity to DTO
    private UserDto userToUserDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());
//        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
