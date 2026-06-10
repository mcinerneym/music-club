package com.github.mcinerneym.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.github.mcinerneym.model.AlbumDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.github.mcinerneym.service.AlbumServiceInterface;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/albums")
public class AlbumController implements AlbumControllerInterface {

    private final AlbumServiceInterface albumService;
    
    @GetMapping("")
    public ResponseEntity<List<AlbumDto>> getAlbums(@RequestParam(required = false) String artist) {
        if (artist != null) {
            log.atDebug().log("Searching for all Albums for artist {}", artist);
            return ResponseEntity.ok(albumService.getAlbumsByArtist(artist));
        }
        log.atDebug().log("Getting All Albums from the DB");
        List<AlbumDto> albums = albumService.getAlbums();
        return ResponseEntity.ok(albums);
    }

    @PostMapping("")
    public ResponseEntity<AlbumDto> addAlbum(@RequestBody AlbumDto album) {
        log.atDebug().log("Adding album {} by {}", album.getName(), album.getArtist());
        AlbumDto savedAlbum = albumService.addAlbum(album);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAlbum);
    }

    @PutMapping("/{albumId}")
    public ResponseEntity<AlbumDto> updateAlbum(@RequestBody AlbumDto album, @PathVariable Long albumId) {
        log.atDebug().log("Updating album {}", albumId);
        album.setId(albumId);
        AlbumDto updatedAlbum = albumService.updateAlbum(album);
        return ResponseEntity.ok(updatedAlbum);
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumDto> getAlbum(@PathVariable Long albumId) {
        log.atDebug().log("Getting Album with ID {}", albumId);
        AlbumDto album = albumService.getAlbum(albumId);
        return ResponseEntity.ok(album);
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long albumId) {
        log.atDebug().log("Deleting Album with ID {}", albumId);
        albumService.deleteAlbum(albumId);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        return response;
    }
}