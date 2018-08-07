package ru.belyaev.vitaliy.albumcheck.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Track {

    @SerializedName("wrapperType")
    @Expose
    private String wrapperType;

    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("artistId")
    @Expose
    private Integer artistId;

    @SerializedName("collectionId")
    @Expose
    private Integer collectionId;

    @SerializedName("trackId")
    @Expose
    private Integer trackId;

    @SerializedName("artistName")
    @Expose
    private String artistName;

    @SerializedName("collectionName")
    @Expose
    private String collectionName;

    @SerializedName("trackName")
    @Expose
    private String trackName;

    @SerializedName("artistViewUrl")
    @Expose
    private String artistViewUrl;

    @SerializedName("collectionViewUrl")
    @Expose
    private String collectionViewUrl;

    @SerializedName("trackViewUrl")
    @Expose
    private String trackViewUrl;

    @SerializedName("previewUrl")
    @Expose
    private String previewUrl;

    @SerializedName("artworkUrl30")
    @Expose
    private String artworkUrl30;

    @SerializedName("artworkUrl60")
    @Expose
    private String artworkUrl60;

    @SerializedName("artworkUrl100")
    @Expose
    private String artworkUrl100;

    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;

    @SerializedName("discCount")
    @Expose
    private Integer discCount;

    @SerializedName("discNumber")
    @Expose
    private Integer discNumber;

    @SerializedName("trackCount")
    @Expose
    private Integer trackCount;

    @SerializedName("trackNumber")
    @Expose
    private Integer trackNumber;

    @SerializedName("trackTimeMillis")
    @Expose
    private Integer trackTimeMillis;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("primaryGenreName")
    @Expose
    private String primaryGenreName;

    public String getWrapperType() {
        return wrapperType;
    }

    public String getKind() {
        return kind;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getArtistViewUrl() {
        return artistViewUrl;
    }

    public String getCollectionViewUrl() {
        return collectionViewUrl;
    }

    public String getTrackViewUrl() {
        return trackViewUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public String getArtworkUrl30() {
        return artworkUrl30;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Integer getDiscCount() {
        return discCount;
    }

    public Integer getDiscNumber() {
        return discNumber;
    }

    public Integer getTrackCount() {
        return trackCount;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public Integer getTrackTimeMillis() {
        return trackTimeMillis;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }
}
