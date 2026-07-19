package com.github.mcinerneym.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user-profile")
@NoArgsConstructor
@Getter
@Setter
public class UserProfile {

    private String email;
    
}
