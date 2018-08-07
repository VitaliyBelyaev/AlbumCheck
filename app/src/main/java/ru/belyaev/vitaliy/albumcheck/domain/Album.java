package ru.belyaev.vitaliy.albumcheck.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Album {

    @SerializedName("wrapperType")
    @Expose
    private String wrapperType;

    @SerializedName("collectionType")
    @Expose
    private String collectionType;

    @SerializedName("artistId")
    @Expose
    private Integer artistId;

    @SerializedName("collectionId")
    @Expose
    private Integer collectionId;

    @SerializedName("artistName")
    @Expose
    private String artistName;

    @SerializedName("collectionName")
    @Expose
    private String collectionName;

    @SerializedName("collectionCensoredName")
    @Expose
    private String collectionCensoredName;

    @SerializedName("artistViewUrl")
    @Expose
    private String artistViewUrl;

    @SerializedName("collectionViewUrl")
    @Expose
    private String collectionViewUrl;

    @SerializedName("artworkUrl60")
    @Expose
    private String artworkUrl60;

    @SerializedName("artworkUrl100")
    @Expose
    private String artworkUrl100;

    @SerializedName("collectionPrice")
    @Expose
    private Double collectionPrice;

    @SerializedName("collectionExplicitness")
    @Expose
    private String collectionExplicitness;

    @SerializedName("contentAdvisoryRating")
    @Expose
    private String contentAdvisoryRating;

    @SerializedName("trackCount")
    @Expose
    private Integer trackCount;

    @SerializedName("copyright")
    @Expose
    private String copyright;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;

    @SerializedName("primaryGenreName")
    @Expose
    private String primaryGenreName;

    public String getWrapperType() {
        return wrapperType;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getCollectionCensoredName() {
        return collectionCensoredName;
    }

    public String getArtistViewUrl() {
        return artistViewUrl;
    }

    public String getCollectionViewUrl() {
        return collectionViewUrl;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public Double getCollectionPrice() {
        return collectionPrice;
    }

    public String getCollectionExplicitness() {
        return collectionExplicitness;
    }

    public String getContentAdvisoryRating() {
        return contentAdvisoryRating;
    }

    public Integer getTrackCount() {
        return trackCount;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }
}
