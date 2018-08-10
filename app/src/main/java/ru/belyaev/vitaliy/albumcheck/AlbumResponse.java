package ru.belyaev.vitaliy.albumcheck;

import java.util.List;

import ru.belyaev.vitaliy.albumcheck.domain.Album;
import ru.belyaev.vitaliy.albumcheck.domain.Track;

public class AlbumResponse {

    private Integer resultCount;
    private List<Album> albums = null;
    private  List<Track> tracks = null;
    private Album album = null;
    private boolean isAlbums = true;

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public boolean isAlbums() {
        return isAlbums;
    }

    public void setIsAlbums(boolean albums) {
        isAlbums = albums;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setAlbums(boolean albums) {
        isAlbums = albums;
    }
}
