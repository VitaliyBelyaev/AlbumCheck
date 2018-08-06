package ru.belyaev.vitaliy.albumcheck;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ItunesApi {

    @GET("/search?entity=album")
    Call<AlbumResponse> searchAlbums(@Query("term") String term);
}
