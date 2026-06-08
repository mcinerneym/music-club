package com.github.mcinerneym.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.github.mcinerneym.model.AlbumDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.github.mcinerneym.service.AlbumServiceInterface;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/albums")
public class AlbumController implements AlbumControllerInterface {

    private final AlbumServiceInterface albumService;
    
    @GetMapping("")
    public ResponseEntity<List<AlbumDto>> getAlbums() {
        log.atDebug().log("Getting All Albums from the DB");
        List<AlbumDto> albums = albumService.getAlbums();
        return ResponseEntity.ok(albums);
    }

    @PostMapping("")
    public ResponseEntity<AlbumDto> addAlbum(@RequestBody AlbumDto album) {
        log.atDebug().log("Adding album %s by %s", album.getName(), album.getArtist());
        AlbumDto savedAlbum = albumService.addAlbum(album);
        return ResponseEntity.status(201).body(savedAlbum);
    }

}