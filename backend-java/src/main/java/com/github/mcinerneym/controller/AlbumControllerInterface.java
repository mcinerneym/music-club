package com.github.mcinerneym.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.github.mcinerneym.model.AlbumDto;

public interface AlbumControllerInterface {

    public ResponseEntity<List<AlbumDto>> getAlbums();

    public ResponseEntity<AlbumDto> addAlbum(AlbumDto album);
    
}