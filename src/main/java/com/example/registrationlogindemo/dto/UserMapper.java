package com.example.registrationlogindemo.dto;

import com.example.registrationlogindemo.entity.User;

public class UserMapper {
    public static UserDto userToDto(User user){

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getName())
                .lastName(user.getName())
                .credito(user.getCredito())
                .roles(user.getRoles())
                .build();
    }

    public static User dtoToUser(UserDto userDto){
        return User.builder()
                .name(userDto.getFirstName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .roles(userDto.getRoles())
                .credito(userDto.getCredito())
                .build();
    }

}
