package ru.belyaev.vitaliy.albumcheck;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.belyaev.vitaliy.albumcheck.domain.Album;
import ru.belyaev.vitaliy.albumcheck.domain.Track;

public class NetworkModule {

    static final String API_HOST = "https://itunes.apple.com/";
    private static final long HTTP_CACHE_SIZE = 1024 * 1024 * 24L; // 24 MB
    private ItunesApi itunesApi;

    public NetworkModule(Context context) {
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

    private Retrofit provideRetrofit(OkHttpClient okHttpClient, String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(getCustomGson()))
                .client(okHttpClient)
                .build();
    }

    private Gson getCustomGson() {
        return new GsonBuilder()
                .registerTypeAdapter(AlbumResponse.class, new AlbumResponseDeserializer())
                .create();
    }

    public class AlbumResponseDeserializer implements JsonDeserializer<AlbumResponse> {
        @Override
        public AlbumResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Gson gson = new Gson();
            AlbumResponse albumResponse = new AlbumResponse();
            JsonObject jsonObject = json.getAsJsonObject();

            for (Map.Entry<String, JsonElement> elementJson : jsonObject.entrySet()) {
                if (elementJson.getKey().equals("resultCount")) {
                    albumResponse.setResultCount(elementJson.getValue().getAsInt());
                } else {
                    JsonArray data = elementJson.getValue().getAsJsonArray();

                    //check if the 2nd element in result array is collection
                    if (data.get(1).getAsJsonObject().get("wrapperType").getAsString().equals("collection")) {
                        List<Album> albums = new ArrayList<>();
                        for (JsonElement element : data) {
                            albums.add(gson.fromJson(element, Album.class));
                        }
                        albumResponse.setIsAlbums(true);
                        albumResponse.setAlbums(albums);
                    } else {
                        data.remove(0);
                        List<Track> tracks = new ArrayList<>();
                        for (JsonElement element : data) {
                            tracks.add(gson.fromJson(element, Track.class));
                        }
                        albumResponse.setIsAlbums(false);
                        albumResponse.setTracks(tracks);
                    }
                }
            }
            return albumResponse;
        }
    }
}
