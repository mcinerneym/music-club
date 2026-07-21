package com.github.mcinerneym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProfileDto {
    //TODO: Need to figure out the id from the token
    private String email;
    private String displayName;
    private String avatarLink;
    private String description;
    
}
