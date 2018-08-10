package ru.belyaev.vitaliy.albumcheck;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import ru.belyaev.vitaliy.albumcheck.domain.Track;

import static ru.belyaev.vitaliy.albumcheck.MainActivity.ALBUM_ID;

public class AlbumActivity extends AppCompatActivity {

    public static final String LOG_TAG = AlbumActivity.class.getName();

    private RecyclerView recyclerView;
    private ImageView albumImage;
    private TextView artistTextView;
    private TextView trackCountTextView;
    private TextView releaseDateTextView;
    private Toolbar toolbar;
    private Album album;
    private TracksAdapter tracksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        initializeUI();

        int albumId = getIntent().getIntExtra(ALBUM_ID, 0);

        getApp().getItunesApi().getAlbum(albumId).enqueue(new Callback<AlbumResponse>() {
            @Override
            public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                if (response.code() == 200) {
                    AlbumResponse albumResponse = response.body();
                    if (!albumResponse.isAlbums()) {
                        List<Track> tracks = albumResponse.getTracks();
                        album = albumResponse.getAlbum();
                        setAlbum(album);
                        getSupportActionBar().setTitle(album.getCollectionName());
                        tracksAdapter.replaceWith(tracks);

                    }
                } else {
                    Log.e(LOG_TAG, "Error with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AlbumResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.album_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = NavUtils.getParentActivityIntent(this);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(this, i);
                return true;

            case R.id.action_open_in_browser:
                Uri webpage = Uri.parse(album.getCollectionViewUrl());
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

                builder
                        .setToolbarColor(ContextCompat.getColor(this, R.color.primaryColor))
                        .addDefaultShareMenuItem();

                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this, webpage);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAlbum(Album album) {

        Picasso.get()
                .load(album.getArtworkUrl100())
                .into(albumImage);

        artistTextView.setText(album.getArtistName());
        String numberOfTracks = getString(R.string.tracks) + String.valueOf(album.getTrackCount());
        trackCountTextView.setText(numberOfTracks);
        releaseDateTextView.setText(formatDate(album.getReleaseDate()));
    }

    private void initializeUI() {
        albumImage = findViewById(R.id.album_image_detailed);
        artistTextView = findViewById(R.id.tv_album_artist_detailed);
        trackCountTextView = findViewById(R.id.tv_track_count_detailed);
        releaseDateTextView = findViewById(R.id.tv_release_date);

        toolbar = findViewById(R.id.album_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_track_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tracksAdapter = new TracksAdapter();
        recyclerView.setAdapter(tracksAdapter);
    }

    private String formatDate(String dateString) {
        try {
            DateFormat inputSdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
            DateFormat outputSdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Date date = inputSdf.parse(dateString);
            return outputSdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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
