package com.github.mcinerneym.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.mcinerneym.model.AlbumDto;
import com.github.mcinerneym.service.AlbumServiceInterface;

@ExtendWith(MockitoExtension.class)
class AlbumControllerTest {

    @Mock
    private AlbumServiceInterface albumService;

    @InjectMocks
    private AlbumController albumController;

    private AlbumDto buildAlbumDto(Long id) {
        return new AlbumDto(id, "Wish You Were Here", "Pink Floyd", List.of("Progressive Rock"),
                LocalDate.of(1975, 9, 12));
    }

    @Test
    void getAlbumsReturnsAllAlbumsWhenArtistIsNotProvided() {
        List<AlbumDto> albums = List.of(buildAlbumDto(1L), buildAlbumDto(2L));
        when(albumService.getAlbums()).thenReturn(albums);

        ResponseEntity<List<AlbumDto>> response = albumController.getAlbums(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isEqualTo(albums);
        verify(albumService, never()).getAlbumsByArtist(any());
    }

    @Test
    void getAlbumsReturnsAlbumsByArtistWhenArtistIsProvided() {
        List<AlbumDto> albums = List.of(buildAlbumDto(1L));
        when(albumService.getAlbumsByArtist("Pink Floyd")).thenReturn(albums);

        ResponseEntity<List<AlbumDto>> response = albumController.getAlbums("Pink Floyd");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isEqualTo(albums);
        verify(albumService, never()).getAlbums();
    }

    @Test
    void addAlbumReturnsCreatedWithSavedAlbum() {
        AlbumDto requestAlbum = buildAlbumDto(null);
        AlbumDto savedAlbum = buildAlbumDto(1L);
        when(albumService.addAlbum(requestAlbum)).thenReturn(savedAlbum);

        ResponseEntity<AlbumDto> response = albumController.addAlbum(requestAlbum);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedAlbum, response.getBody());
    }

    @Test
    void updateAlbumSetsPathIdOnAlbumAndReturnsUpdatedAlbum() {
        AlbumDto requestAlbum = buildAlbumDto(null);
        AlbumDto updatedAlbum = buildAlbumDto(1L);
        when(albumService.updateAlbum(any(AlbumDto.class))).thenReturn(updatedAlbum);

        ResponseEntity<AlbumDto> response = albumController.updateAlbum(requestAlbum, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAlbum, response.getBody());
        assertEquals(1L, requestAlbum.getId());
        verify(albumService, times(1)).updateAlbum(requestAlbum);
    }

    @Test
    void getAlbumReturnsAlbumForGivenId() {
        AlbumDto album = buildAlbumDto(1L);
        when(albumService.getAlbum(1L)).thenReturn(album);

        ResponseEntity<AlbumDto> response = albumController.getAlbum(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(album, response.getBody());
    }

    @Test
    void deleteAlbumReturnsNoContentAndDelegatesToService() {
        ResponseEntity<Void> response = albumController.deleteAlbum(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertThat(response.getBody()).isNull();
        verify(albumService, times(1)).deleteAlbum(eq(1L));
    }
}
