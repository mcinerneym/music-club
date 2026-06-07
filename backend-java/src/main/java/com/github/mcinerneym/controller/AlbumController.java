package com.github.mcinerneym.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import com.github.mcinerneym.model.AlbumDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.github.mcinerneym.service.AlbumServiceInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/albums")
public class AlbumController implements AlbumControllerInterface {

    private final AlbumServiceInterface albumService;
    
    @GetMapping("")
    public ResponseEntity<List<AlbumDto>> getAlbums() {
        List<AlbumDto> albums = albumService.getAlbums();
        return ResponseEntity.ok(albums);
    }

}