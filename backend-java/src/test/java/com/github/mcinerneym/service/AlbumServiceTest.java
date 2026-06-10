package com.github.mcinerneym.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.mcinerneym.exceptions.AlbumNotFoundException;
import com.github.mcinerneym.exceptions.DuplicateAlbumException;
import com.github.mcinerneym.model.Album;
import com.github.mcinerneym.model.AlbumDto;
import com.github.mcinerneym.repository.AlbumRepository;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumService albumService;

    private AlbumDto buildAlbumDto(Long id) {
        return new AlbumDto(id, "Wish You Were Here", "Pink Floyd", List.of("Progressive Rock"),
                LocalDate.of(1975, 9, 12));
    }

    private Album buildAlbumEntity(Long id) {
        Album album = new Album();
        album.setId(id);
        album.setName("Wish You Were Here");
        album.setArtist("Pink Floyd");
        album.setGenres("Progressive Rock");
        album.setReleaseDate(java.sql.Date.valueOf(LocalDate.of(1975, 9, 12)));
        return album;
    }

    @Test
    void getAlbumsReturnsMappedAlbumsWhenRepositoryHasResults() {
        Album album = buildAlbumEntity(1L);
        when(albumRepository.findAll()).thenReturn(List.of(album));

        List<AlbumDto> result = albumService.getAlbums();

        assertThat(result).hasSize(1);
        AlbumDto albumDto = result.get(0);
        assertEquals(1L, albumDto.getId());
        assertEquals("Wish You Were Here", albumDto.getName());
        assertEquals("Pink Floyd", albumDto.getArtist());
        assertThat(albumDto.getGenres()).containsExactly("Progressive Rock");
        assertEquals(LocalDate.of(1975, 9, 12), albumDto.getReleaseDate());
    }

    @Test
    void getAlbumsReturnsEmptyListWhenRepositoryHasNoResults() {
        when(albumRepository.findAll()).thenReturn(List.of());

        List<AlbumDto> result = albumService.getAlbums();

        assertThat(result).isEmpty();
    }

    @Test
    void addAlbumThrowsNullPointerExceptionWhenAlbumDtoIsNull() {
        assertThrows(NullPointerException.class, () -> albumService.addAlbum(null));
        verifyNoInteractions(albumRepository);
    }

    @Test
    void addAlbumThrowsDuplicateAlbumExceptionWhenAlbumAlreadyExists() {
        AlbumDto albumDto = buildAlbumDto(null);
        when(albumRepository.existsByNameIgnoreCaseAndArtistIgnoreCase("Wish You Were Here", "Pink Floyd"))
                .thenReturn(true);

        DuplicateAlbumException exception = assertThrows(DuplicateAlbumException.class,
                () -> albumService.addAlbum(albumDto));

        assertEquals("Album 'Wish You Were Here' by 'Pink Floyd' already exists", exception.getMessage());
        verify(albumRepository, never()).save(any());
    }

    @Test
    void addAlbumSavesAndReturnsMappedAlbumWhenItDoesNotExist() {
        AlbumDto albumDto = buildAlbumDto(null);
        Album savedEntity = buildAlbumEntity(1L);
        when(albumRepository.existsByNameIgnoreCaseAndArtistIgnoreCase("Wish You Were Here", "Pink Floyd"))
                .thenReturn(false);
        when(albumRepository.save(any(Album.class))).thenReturn(savedEntity);

        AlbumDto result = albumService.addAlbum(albumDto);

        assertEquals(1L, result.getId());
        assertEquals("Wish You Were Here", result.getName());
        assertEquals("Pink Floyd", result.getArtist());
        assertThat(result.getGenres()).containsExactly("Progressive Rock");
        assertEquals(LocalDate.of(1975, 9, 12), result.getReleaseDate());
    }

    @Test
    void updateAlbumThrowsNullPointerExceptionWhenAlbumDtoIsNull() {
        assertThrows(NullPointerException.class, () -> albumService.updateAlbum(null));
        verifyNoInteractions(albumRepository);
    }

    @Test
    void updateAlbumThrowsAlbumNotFoundExceptionWhenAlbumDoesNotExist() {
        AlbumDto albumDto = buildAlbumDto(99L);
        when(albumRepository.existsById(99L)).thenReturn(false);

        AlbumNotFoundException exception = assertThrows(AlbumNotFoundException.class,
                () -> albumService.updateAlbum(albumDto));

        assertEquals("Album with Id '99' does not exist.", exception.getMessage());
        verify(albumRepository, never()).save(any());
    }

    @Test
    void updateAlbumSavesAndReturnsMappedAlbumWhenItExists() {
        AlbumDto albumDto = buildAlbumDto(1L);
        Album savedEntity = buildAlbumEntity(1L);
        when(albumRepository.existsById(1L)).thenReturn(true);
        when(albumRepository.save(any(Album.class))).thenReturn(savedEntity);

        AlbumDto result = albumService.updateAlbum(albumDto);

        assertEquals(1L, result.getId());
        assertEquals("Wish You Were Here", result.getName());
        assertEquals("Pink Floyd", result.getArtist());
    }

    @Test
    void getAlbumThrowsNullPointerExceptionWhenIdIsNull() {
        assertThrows(NullPointerException.class, () -> albumService.getAlbum(null));
        verifyNoInteractions(albumRepository);
    }

    @Test
    void getAlbumThrowsAlbumNotFoundExceptionWhenAlbumDoesNotExist() {
        when(albumRepository.findById(99L)).thenReturn(Optional.empty());

        AlbumNotFoundException exception = assertThrows(AlbumNotFoundException.class,
                () -> albumService.getAlbum(99L));

        assertEquals("Album with Id '99' does not exist.", exception.getMessage());
    }

    @Test
    void getAlbumReturnsMappedAlbumWhenItExists() {
        Album album = buildAlbumEntity(1L);
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));

        AlbumDto result = albumService.getAlbum(1L);

        assertEquals(1L, result.getId());
        assertEquals("Wish You Were Here", result.getName());
    }

    @Test
    void getAlbumsByArtistThrowsNullPointerExceptionWhenArtistIsNull() {
        assertThrows(NullPointerException.class, () -> albumService.getAlbumsByArtist(null));
        verifyNoInteractions(albumRepository);
    }

    @Test
    void getAlbumsByArtistReturnsEmptyListWhenRepositoryReturnsNull() {
        when(albumRepository.findByArtistIgnoreCase("Pink Floyd")).thenReturn(null);

        List<AlbumDto> result = albumService.getAlbumsByArtist("Pink Floyd");

        assertThat(result).isEmpty();
    }

    @Test
    void getAlbumsByArtistReturnsEmptyListWhenRepositoryReturnsEmptyList() {
        when(albumRepository.findByArtistIgnoreCase("Pink Floyd")).thenReturn(List.of());

        List<AlbumDto> result = albumService.getAlbumsByArtist("Pink Floyd");

        assertThat(result).isEmpty();
    }

    @Test
    void getAlbumsByArtistReturnsMappedAlbumsWhenRepositoryHasResults() {
        Album album = buildAlbumEntity(1L);
        when(albumRepository.findByArtistIgnoreCase("Pink Floyd")).thenReturn(List.of(album));

        List<AlbumDto> result = albumService.getAlbumsByArtist("Pink Floyd");

        assertThat(result).hasSize(1);
        assertEquals("Pink Floyd", result.get(0).getArtist());
    }

    @Test
    void deleteAlbumThrowsNullPointerExceptionWhenIdIsNull() {
        assertThrows(NullPointerException.class, () -> albumService.deleteAlbum(null));
        verifyNoInteractions(albumRepository);
    }

    @Test
    void deleteAlbumDelegatesToRepository() {
        albumService.deleteAlbum(1L);

        verify(albumRepository, times(1)).deleteById(eq(1L));
    }
}
