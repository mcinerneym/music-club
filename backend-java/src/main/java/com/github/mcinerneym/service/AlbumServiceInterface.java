package com.github.mcinerneym.service;

import java.util.List;

import com.github.mcinerneym.model.AlbumDto;

public interface AlbumServiceInterface {
    public List<AlbumDto> getAlbums();

    public AlbumDto addAlbum(AlbumDto albumDto);

    public AlbumDto updateAlbum(AlbumDto albumDto);

    public AlbumDto getAlbum(Long albumId);
}
