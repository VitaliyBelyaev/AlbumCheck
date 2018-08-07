package ru.belyaev.vitaliy.albumcheck;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ItunesApi {

    @GET("/search?media=music&entity=album")
    Call<AlbumResponse> searchAlbums(@Query("term") String term);

    @GET("/lookup?entity=song")
    Call<AlbumResponse> getAlbum(@Query("id") int id);
}
