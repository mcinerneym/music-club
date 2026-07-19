package com.github.mcinerneym.service;

import com.github.mcinerneym.model.UserDto;
import com.github.mcinerneym.model.UserProfileDto;

public interface UserServiceInterface {

    public UserProfileDto createUser(UserDto user);
    
}
