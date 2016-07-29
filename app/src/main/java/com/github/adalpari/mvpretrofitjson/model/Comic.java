package com.github.adalpari.mvpretrofitjson.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by plaza.a on 13/07/2016.
 * <p/>
 * Class to represen RELEVANT info about a comic
 */
public class Comic implements Parcelable {

    private String title;
    private String description;
    private String picture;
    private List<String> imagesURL;

    public Comic(String title, String description, String picture) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.imagesURL = new ArrayList<String>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void addImageURL(String url) {
        imagesURL.add(url);
    }

    /**
     * Get random url from imagesURLS
     *
     * @return the String of URL
     */
    public String getRandomImage() {
        Random r = new Random();
        //nextInt(max - min) + min
        int i = r.nextInt(imagesURL.size() - 0);        //integer between 0 (inclusive) and size (exclusive)

        return imagesURL.get(i);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(picture);
        dest.writeList(imagesURL);
    }

    private Comic(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.picture = in.readString();
        imagesURL = new ArrayList<String>();
        in.readList(imagesURL, String.class.getClassLoader());
    }

    public static final Parcelable.Creator<Comic> CREATOR = new Parcelable.Creator<Comic>() {

        @Override
        public Comic createFromParcel(Parcel source) {
            return new Comic(source);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };
}
