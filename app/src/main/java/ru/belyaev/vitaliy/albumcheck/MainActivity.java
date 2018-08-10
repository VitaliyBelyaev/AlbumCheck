package ru.belyaev.vitaliy.albumcheck;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.belyaev.vitaliy.albumcheck.domain.Album;

public class MainActivity extends AppCompatActivity
        implements AlbumsAdapter.AlbumOnClickHandler,
        SortDialogFragment.SortDialogListener {

    public static final String LOG_TAG = MainActivity.class.getName();
    public static final String ALBUM_ID = "album_id";
    private AlbumsAdapter albumsAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        initUI();
        showStatusMessage(getString(R.string.empty_message));

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            if(isConnected()){
                showSearching();
                handleSearchIntent(intent);
            } else{
                showStatusMessage(getString(R.string.no_internet_conn));
            }

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            handleSearchIntent(intent);
        }
        super.onNewIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort:
                if (albumsAdapter.hasData()) {
                    showSortDialog();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSortDialog() {
        DialogFragment dialog = new SortDialogFragment();
        dialog.show(getSupportFragmentManager(), "SortDialogFragment");
    }


    private void searchAlbums(String query) {

        getApp().getItunesApi().searchAlbums(query).enqueue(new Callback<AlbumResponse>() {
            @Override
            public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                if (response.code() == 200) {
                    AlbumResponse albumResponse = response.body();
                    if (albumResponse.isAlbums() && albumResponse.getAlbums() != null) {
                        List<Album> albums = albumResponse.getAlbums();
                        albumsAdapter.setDefaultOrderedAlbums(albums);
                        showContent();
                        albumsAdapter.defaultOrder();
                    } else {
                        showStatusMessage(getString(R.string.search_null_result));
                    }
                } else {
                    showStatusMessage(getString(R.string.error_message));
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

    @Override
    public void onSortItemClick(int sortMode) {
        switch (sortMode) {
            case Constants.SORT.DEFAULT_ORDER:
                albumsAdapter.defaultOrder();
                break;
            case Constants.SORT.DATE:
                albumsAdapter.sortByDate(false);
                break;
            case Constants.SORT.DATE_REVERSE:
                albumsAdapter.sortByDate(true);
                break;
            case Constants.SORT.ALPHABETICALLY:
                albumsAdapter.sortByName(false);
                break;
            case Constants.SORT.ALPHABETICALLY_REVERSE:
                albumsAdapter.sortByName(true);
                break;
        }
    }

    private void handleSearchIntent(Intent intent) {
        String query = intent.getStringExtra(SearchManager.QUERY);
        searchAlbums(query);
    }

    private boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void showSearching(){
        recyclerView.setVisibility(View.GONE);
        statusText.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showContent(){
        statusText.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void  showStatusMessage(String message){
        statusText.setText(message);
        statusText.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    private void initUI() {
        progressBar = findViewById(R.id.progress_bar);
        statusText = findViewById(R.id.status_text);

        recyclerView = findViewById(R.id.rv_search_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        albumsAdapter = new AlbumsAdapter(this);
        recyclerView.setAdapter(albumsAdapter);
    }


    private App getApp() {
        return ((App) getApplication());
    }
}
