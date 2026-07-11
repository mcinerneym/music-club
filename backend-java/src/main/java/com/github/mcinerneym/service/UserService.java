package com.github.mcinerneym.service;

import org.springframework.stereotype.Service;

import com.github.mcinerneym.repository.UserRepostiory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserServiceInterface {

    private final UserRepostiory userRepository;
    
}
