package ru.belyaev.vitaliy.albumcheck;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.belyaev.vitaliy.albumcheck.domain.Album;

public class AlbumResponse {

    @SerializedName("resultCount")
    @Expose
    private Integer resultCount;

    @SerializedName("results")
    @Expose
    private List<Album> albums = null;

    public Integer getResultCount() {
        return resultCount;
    }

    public List<Album> getAlbums() {
        return albums;
    }
}
