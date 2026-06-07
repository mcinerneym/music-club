package com.github.mcinerneym.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.mcinerneym.model.Album;
import com.github.mcinerneym.model.AlbumDto;
import com.github.mcinerneym.repository.AlbumRepository;
import com.github.mcinerneym.mapper.AlbumMapper;

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


    
}
