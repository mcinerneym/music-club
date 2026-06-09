package com.github.mcinerneym.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.github.mcinerneym.model.Album;
import com.github.mcinerneym.model.AlbumDto;

public final class AlbumMapper {

    private AlbumMapper(){}
    
    public static Album toEntity(AlbumDto albumDto) {
        Album album = newEntity(albumDto);
        album.setId(albumDto.getId());
        return album;
    }

    public static Album newEntity(AlbumDto albumDto) {
        Album album = new Album();

        String genres = String.join(",", albumDto.getGenres());
        java.sql.Date albumDate = null;
        if (albumDto.getReleaseDate() != null) {
            albumDate = java.sql.Date.valueOf(albumDto.getReleaseDate());
        }
        album.setName(albumDto.getName());
        album.setArtist(albumDto.getArtist());
        album.setGenres(genres);
        album.setReleaseDate(albumDate);

        return album;
    }

    public static List<Album> toEntities(List<AlbumDto> albumDtoList) {
        return albumDtoList.stream()
                .map(albumDto -> toEntity(albumDto))
                .toList();
    }

    public static AlbumDto fromEntity(Album album) {
        AlbumDto albumDto = new AlbumDto();
        String albumGenres = album.getGenres();
        List<String> albumDtoGenres = new ArrayList<>();
        if (albumGenres != null && !albumGenres.isBlank()){ 
            albumDtoGenres = List.of(album.getGenres().split(","));
        }
        LocalDate albumDate = null;
        if (album.getReleaseDate() != null){
            albumDate = album.getReleaseDate().toLocalDate();
        }
        albumDto.setId(album.getId());
        albumDto.setName(album.getName());
        albumDto.setArtist(album.getArtist());
        albumDto.setGenres(albumDtoGenres);
        albumDto.setReleaseDate(albumDate);
        return albumDto;
    }

    public static List<AlbumDto> fromEntities(List<Album> albumList) {
        return albumList.stream()
                .map(album -> fromEntity(album))
                .toList();
    }
}
