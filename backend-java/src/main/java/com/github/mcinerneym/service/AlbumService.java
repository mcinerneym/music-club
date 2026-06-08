package com.github.mcinerneym.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.mcinerneym.model.Album;
import com.github.mcinerneym.model.AlbumDto;
import com.github.mcinerneym.repository.AlbumRepository;
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
            log.atError().log("Album '%s' by '%s' already exists", albumName, albumArtist);
            throw new DuplicateAlbumException("Album '%s' by '%s' already exists".formatted(albumName, albumArtist));
        }
        Album album = AlbumMapper.newEntity(albumDto);
        log.atInfo().log("Logging the album %s", album.getName());
        Album entity = albumRepository.save(album);

        return AlbumMapper.fromEntity(entity);
    }


    
}
