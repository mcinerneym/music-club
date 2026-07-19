package com.github.mcinerneym.service;

import org.springframework.stereotype.Service;

import com.github.mcinerneym.model.UserDto;
import com.github.mcinerneym.model.UserProfileDto;
import com.github.mcinerneym.repository.UserProfileRepostiory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserServiceInterface {

    private final UserProfileRepostiory userProfileRepository;

    public UserProfileDto createUser(UserDto user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }
}
