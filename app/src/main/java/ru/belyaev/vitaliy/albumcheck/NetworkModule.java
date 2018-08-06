package ru.belyaev.vitaliy.albumcheck;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule {

    public static final String API_HOST = "https://itunes.apple.com/";
    private static final long HTTP_CACHE_SIZE = 1024 * 1024 * 24L; // 24 MB

    private ItunesApi itunesApi;

    public NetworkModule(Context context){
        final OkHttpClient okHttpClient = provideOkHttp(context.getCacheDir());
        final Retrofit apiRetrofit = provideRetrofit(okHttpClient, API_HOST);
        itunesApi = apiRetrofit.create(ItunesApi.class);
    }

    public ItunesApi getItunesApi() {
        return itunesApi;
    }

    private OkHttpClient provideOkHttp(File cacheDir) {
        final HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("OkHttp", message);
            }
        };

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger) //
                .setLevel(HttpLoggingInterceptor.Level.HEADERS);

        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .cache(new Cache(new File(cacheDir, "http"), HTTP_CACHE_SIZE))
                .build();

    }

    private Retrofit provideRetrofit(OkHttpClient okHttpClient, String baseUrl){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
