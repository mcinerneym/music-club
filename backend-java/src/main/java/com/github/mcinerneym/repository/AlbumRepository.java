package com.github.mcinerneym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.mcinerneym.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    boolean existsByNameIgnoreCaseAndArtistIgnoreCase(String name, String artist);
}