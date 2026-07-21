package com.github.mcinerneym.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @Nonnull
    private String email;
    @Nonnull
    private String password;
    private String displayName;
    @JsonIgnore
    private LocalDateTime creationDateTime;

}
