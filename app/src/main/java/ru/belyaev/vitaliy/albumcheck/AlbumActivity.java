package ru.belyaev.vitaliy.albumcheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.belyaev.vitaliy.albumcheck.domain.Album;

import static ru.belyaev.vitaliy.albumcheck.MainActivity.ALBUM_ID;

public class AlbumActivity extends AppCompatActivity {

    private ImageView albumImage;
    private TextView titleTextView;
    private TextView artistTextView;
    private TextView trackCountTextView;
    private TextView primaryGenreTextView;
    private TextView releaseDateTextView;
    private TextView copyrightTextView;
    public static final String LOG_TAG = AlbumActivity.class.getName();

    private Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        setViews();
        int albumId = getIntent().getIntExtra(ALBUM_ID, 0);

        getApp().getItunesApi().getAlbum(albumId).enqueue(new Callback<AlbumResponse>() {
            @Override
            public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                if (response.code() == 200) {
                    List<Album> albums = response.body().getAlbums();
                    album = albums.get(0);

                    setAlbum();
                } else {
                    Log.e(LOG_TAG, "Error with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AlbumResponse> call, Throwable t) {

            }
        });




    }

    private void setViews() {
        albumImage = findViewById(R.id.album_image_detailed);
        titleTextView = findViewById(R.id.tv_album_title_detailed);
        artistTextView = findViewById(R.id.tv_album_artist_detailed);
        trackCountTextView = findViewById(R.id.tv_track_count_detailed);
        primaryGenreTextView = findViewById(R.id.tv_primary_genre);
        releaseDateTextView = findViewById(R.id.tv_release_date);
        copyrightTextView = findViewById(R.id.tv_copyright);
    }

    private void setAlbum() {
        Picasso.get()
                .load(album.getArtworkUrl100())
                .into(albumImage);

        titleTextView.setText(album.getCollectionName());
        artistTextView.setText(album.getArtistName());

        String tracks = String.valueOf(album.getTrackCount())+" tracks";
        trackCountTextView.setText(tracks);
        primaryGenreTextView.setText(album.getPrimaryGenreName());
        try{
            DateFormat inputSdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
            DateFormat outputSdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Date date = inputSdf.parse(album.getReleaseDate());
            String releaseDate = outputSdf.format(date);
            Log.i("Date",releaseDate);
            releaseDateTextView.setText(releaseDate);
        } catch (ParseException e){
            e.printStackTrace();
        }

        copyrightTextView.setText(album.getCopyright());
    }

    public static void start(Activity activity, int albumId) {
        Intent intent = new Intent(activity, AlbumActivity.class);
        intent.putExtra(ALBUM_ID, albumId);
        activity.startActivity(intent);
    }

    private App getApp() {
        return ((App) getApplication());
    }
}
