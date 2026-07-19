package com.github.mcinerneym.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mcinerneym.model.UserDto;
import com.github.mcinerneym.model.UserProfileDto;
import com.github.mcinerneym.service.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController implements UserControllerInterface {
    private final UserServiceInterface userService;

    @PostMapping("")
    public UserProfileDto createUser(@RequestBody UserDto user) {
        log.atInfo().log("Creating user with email: {}", user.getEmail());
        
        UserProfileDto userProfile = userService.createUser(user);
        return userProfile;
    }
}
