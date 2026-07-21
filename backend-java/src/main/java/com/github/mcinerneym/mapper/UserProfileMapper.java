package com.github.mcinerneym.mapper;

import java.sql.Date;

import com.github.mcinerneym.model.UserDto;
import com.github.mcinerneym.model.UserProfile;

public final class UserProfileMapper {

    private UserProfileMapper(){};

    public static UserProfile fromNewUser(UserDto user) {
        UserProfile userProfile = new UserProfile();

        userProfile.setEmail(user.getEmail());
        userProfile.setDisplayName(user.getDisplayName());
        userProfile.setCreationDate(Date.valueOf(user.getCreationDateTime().toString()));

        return userProfile;
    }
    
}
