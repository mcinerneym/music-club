package com.github.mcinerneym.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDto {
    private Long id;
    @Nonnull
    private String name;
    @Nonnull
    private String artist;
    private List<String> genres;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
}