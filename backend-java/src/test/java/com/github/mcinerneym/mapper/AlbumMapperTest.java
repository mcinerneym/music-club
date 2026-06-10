package com.github.mcinerneym.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.mcinerneym.model.Album;
import com.github.mcinerneym.model.AlbumDto;

class AlbumMapperTest {

    @Test
    void newEntityMapsAllFieldsIncludingGenresAndReleaseDate() {
        AlbumDto albumDto = new AlbumDto(5L, "The Wall", "Pink Floyd", List.of("Rock", "Progressive Rock"),
                LocalDate.of(1979, 11, 30));

        Album album = AlbumMapper.newEntity(albumDto);

        assertNull(album.getId());
        assertEquals("The Wall", album.getName());
        assertEquals("Pink Floyd", album.getArtist());
        assertEquals("Rock,Progressive Rock", album.getGenres());
        assertEquals(java.sql.Date.valueOf(LocalDate.of(1979, 11, 30)), album.getReleaseDate());
    }

    @Test
    void newEntityHandlesEmptyGenresAndNullReleaseDate() {
        AlbumDto albumDto = new AlbumDto(null, "Animals", "Pink Floyd", List.of(), null);

        Album album = AlbumMapper.newEntity(albumDto);

        assertEquals("", album.getGenres());
        assertNull(album.getReleaseDate());
    }

    @Test
    void toEntitySetsIdInAdditionToOtherFields() {
        AlbumDto albumDto = new AlbumDto(7L, "Animals", "Pink Floyd", List.of("Progressive Rock"),
                LocalDate.of(1977, 1, 23));

        Album album = AlbumMapper.toEntity(albumDto);

        assertEquals(7L, album.getId());
        assertEquals("Animals", album.getName());
        assertEquals("Pink Floyd", album.getArtist());
        assertEquals("Progressive Rock", album.getGenres());
        assertEquals(java.sql.Date.valueOf(LocalDate.of(1977, 1, 23)), album.getReleaseDate());
    }

    @Test
    void toEntitiesMapsEachAlbumDtoToAnEntity() {
        AlbumDto first = new AlbumDto(1L, "Animals", "Pink Floyd", List.of("Rock"), LocalDate.of(1977, 1, 23));
        AlbumDto second = new AlbumDto(2L, "The Wall", "Pink Floyd", List.of("Rock"), LocalDate.of(1979, 11, 30));

        List<Album> albums = AlbumMapper.toEntities(List.of(first, second));

        assertThat(albums).hasSize(2);
        assertEquals(1L, albums.get(0).getId());
        assertEquals(2L, albums.get(1).getId());
    }

    @Test
    void fromEntityMapsAllFieldsIncludingGenresAndReleaseDate() {
        Album album = new Album();
        album.setId(3L);
        album.setName("Wish You Were Here");
        album.setArtist("Pink Floyd");
        album.setGenres("Rock,Progressive Rock");
        album.setReleaseDate(java.sql.Date.valueOf(LocalDate.of(1975, 9, 12)));

        AlbumDto albumDto = AlbumMapper.fromEntity(album);

        assertEquals(3L, albumDto.getId());
        assertEquals("Wish You Were Here", albumDto.getName());
        assertEquals("Pink Floyd", albumDto.getArtist());
        assertThat(albumDto.getGenres()).containsExactly("Rock", "Progressive Rock");
        assertEquals(LocalDate.of(1975, 9, 12), albumDto.getReleaseDate());
    }

    @Test
    void fromEntityReturnsEmptyGenresListWhenGenresIsNull() {
        Album album = new Album();
        album.setId(4L);
        album.setName("Animals");
        album.setArtist("Pink Floyd");
        album.setGenres(null);
        album.setReleaseDate(null);

        AlbumDto albumDto = AlbumMapper.fromEntity(album);

        assertThat(albumDto.getGenres()).isEmpty();
        assertNull(albumDto.getReleaseDate());
    }

    @Test
    void fromEntityReturnsEmptyGenresListWhenGenresIsBlank() {
        Album album = new Album();
        album.setId(4L);
        album.setName("Animals");
        album.setArtist("Pink Floyd");
        album.setGenres("");

        AlbumDto albumDto = AlbumMapper.fromEntity(album);

        assertThat(albumDto.getGenres()).isEmpty();
    }

    @Test
    void fromEntitiesMapsEachAlbumToAnAlbumDto() {
        Album first = new Album();
        first.setId(1L);
        first.setName("Animals");
        first.setArtist("Pink Floyd");
        first.setGenres("Rock");

        Album second = new Album();
        second.setId(2L);
        second.setName("The Wall");
        second.setArtist("Pink Floyd");
        second.setGenres("Rock");

        List<AlbumDto> albumDtos = AlbumMapper.fromEntities(List.of(first, second));

        assertThat(albumDtos).hasSize(2);
        assertEquals(1L, albumDtos.get(0).getId());
        assertEquals(2L, albumDtos.get(1).getId());
    }
}
