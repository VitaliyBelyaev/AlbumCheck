package ru.belyaev.vitaliy.albumcheck;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements AlbumsAdapter.AlbumOnClickHandler {

    private EditText queryEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private AlbumsAdapter albumsAdapter;
    public static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryEditText = findViewById(R.id.et_query);
        searchButton = findViewById(R.id.button_search);
        initRecyclerView();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAlbums(queryEditText.getText().toString());
            }
        });
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
    public void onClick(int albumIndex) {

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
