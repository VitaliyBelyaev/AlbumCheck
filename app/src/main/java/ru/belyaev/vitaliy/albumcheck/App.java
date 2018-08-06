package ru.belyaev.vitaliy.albumcheck;

import android.app.Application;

public class App extends Application {

    private ItunesApi itunesApi;

    @Override
    public void onCreate() {
        super.onCreate();

        NetworkModule networkModule = new NetworkModule(this);
        itunesApi = networkModule.getItunesApi();
    }

    public ItunesApi getItunesApi() {
        return itunesApi;
    }
}
