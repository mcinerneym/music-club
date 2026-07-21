package com.github.mcinerneym.model;

import java.sql.Date;

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
    private String displayName;
    private String avatarLink;
    private Date creationDate;
    
}
