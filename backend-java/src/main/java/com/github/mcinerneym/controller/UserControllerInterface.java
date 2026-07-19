package com.github.mcinerneym.controller;

import com.github.mcinerneym.model.UserDto;
import com.github.mcinerneym.model.UserProfileDto;


public interface UserControllerInterface {

    public UserProfileDto createUser (UserDto user);
    
}
