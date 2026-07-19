package com.github.mcinerneym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.mcinerneym.model.UserProfile;

public interface UserProfileRepostiory extends JpaRepository<UserProfile, Long>{
    
}
