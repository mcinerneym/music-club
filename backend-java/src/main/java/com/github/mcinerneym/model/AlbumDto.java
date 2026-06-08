package com.github.mcinerneym.model;

import java.util.Date;
import java.util.List;

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
    private Date releaseDate;
}