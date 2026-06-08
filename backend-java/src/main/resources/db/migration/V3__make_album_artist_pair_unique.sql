ALTER TABLE album
ADD CONSTRAINT uq_album_name_artist UNIQUE (name, artist);

CREATE UNIQUE INDEX uq_album_name_artist_ci ON album (LOWER(name), LOWER(artist));