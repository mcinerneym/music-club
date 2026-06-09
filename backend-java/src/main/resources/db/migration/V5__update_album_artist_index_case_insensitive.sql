DROP INDEX album_artist;

CREATE INDEX idx_album_artist ON album(LOWER(artist));