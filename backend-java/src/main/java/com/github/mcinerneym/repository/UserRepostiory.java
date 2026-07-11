package com.github.mcinerneym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.mcinerneym.model.User;

public interface UserRepostiory extends JpaRepository<User, Long>{
    
}
