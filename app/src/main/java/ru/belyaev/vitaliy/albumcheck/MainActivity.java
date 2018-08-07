package ru.belyaev.vitaliy.albumcheck;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.belyaev.vitaliy.albumcheck.domain.Album;

public class MainActivity extends AppCompatActivity
        implements AlbumsAdapter.AlbumOnClickHandler {

    private RecyclerView recyclerView;
    private AlbumsAdapter albumsAdapter;
    private Toolbar toolbar;
    public static final String LOG_TAG = MainActivity.class.getName();
    public static final String ALBUM_ID = "album_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleIntent(getIntent());

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        initRecyclerView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    private void searchAlbums(String query) {

        getApp().getItunesApi().searchAlbums(query).enqueue(new Callback<AlbumResponse>() {
            @Override
            public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                if (response.code() == 200) {
                    List<Album> albums = response.body().getAlbums();
                    albumsAdapter.replaceWith(albums);
                } else {
                    Log.e(LOG_TAG, "Error with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AlbumResponse> call, Throwable t) {
                Log.e(LOG_TAG, "Error: " + t.toString());
            }
        });
    }

    @Override
    public void onClick(int position, int albumId) {
        AlbumActivity.start(this, albumId);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchAlbums(query);
        }
    }


    private void initRecyclerView() {
        recyclerView = findViewById(R.id.rv_search_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        albumsAdapter = new AlbumsAdapter(this);
        recyclerView.setAdapter(albumsAdapter);
    }


    private App getApp() {
        return ((App) getApplication());
    }

}
