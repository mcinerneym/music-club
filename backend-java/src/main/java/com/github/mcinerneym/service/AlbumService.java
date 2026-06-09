package com.github.mcinerneym.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.mcinerneym.model.Album;
import com.github.mcinerneym.model.AlbumDto;
import com.github.mcinerneym.repository.AlbumRepository;
import com.github.mcinerneym.exceptions.AlbumNotFoundException;
import com.github.mcinerneym.exceptions.DuplicateAlbumException;
import com.github.mcinerneym.mapper.AlbumMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumService implements AlbumServiceInterface {

    private final AlbumRepository albumRepository;

    public List<AlbumDto> getAlbums() {
        List<Album> albumList = albumRepository.findAll();
        List<AlbumDto> albumDtoList = AlbumMapper.fromEntities(albumList);
        return albumDtoList;
    }

    public AlbumDto addAlbum(@NonNull AlbumDto albumDto){
        String albumName = albumDto.getName();
        String albumArtist = albumDto.getArtist();
        boolean albumExists = albumRepository.existsByNameIgnoreCaseAndArtistIgnoreCase(albumName,albumArtist);
        if (albumExists) {
            log.atError().log("Album '{}' by '{}' already exists", albumName, albumArtist);
            throw new DuplicateAlbumException("Album '%s' by '%s' already exists".formatted(albumName, albumArtist));
        }
        Album album = AlbumMapper.newEntity(albumDto);
        log.atInfo().log("Logging the album %s", album.getName());
        Album entity = albumRepository.save(album);

        return AlbumMapper.fromEntity(entity);
    }

    public AlbumDto updateAlbum(@NonNull AlbumDto albumDto) {
        boolean albumExists = albumRepository.existsById(albumDto.getId());
        if (!albumExists) {
            log.atError().log("Attempted to update: Album with Id '{}' does not exist.", albumDto.getId());
            throw new AlbumNotFoundException("Album with Id '%d' does not exist.".formatted(albumDto.getId()));
        }
        Album album = AlbumMapper.toEntity(albumDto);
        Album entity = albumRepository.save(album);
        
        return AlbumMapper.fromEntity(entity);
    }

    public AlbumDto getAlbum(@NonNull Long albumId) {
        Album album = albumRepository.findById(albumId).orElseGet(() -> null);
        if (album == null) {
            log.atError().log("Attempted to get: Album with Id '{}' does not exist.", albumId);
            throw new AlbumNotFoundException("Album with Id '%d' does not exist.".formatted(albumId));
        }

        return AlbumMapper.fromEntity(album);
    }

    public List<AlbumDto> getAlbumsByArtist(String artist) {
        List<Album> albums = albumRepository.findByArtistIgnoreCase(artist);
        if (albums == null || albums.isEmpty()) {
            return List.of();
        }
        return AlbumMapper.fromEntities(albums);

    }
}
