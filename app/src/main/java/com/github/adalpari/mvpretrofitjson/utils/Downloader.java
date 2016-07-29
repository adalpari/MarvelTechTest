package com.github.adalpari.mvpretrofitjson.utils;

import com.github.adalpari.mvpretrofitjson.interfaces.ComicsDownloadInterface;
import com.github.adalpari.mvpretrofitjson.model.APIResponse;
import com.github.adalpari.mvpretrofitjson.model.Comic;
import com.github.adalpari.mvpretrofitjson.model.Image;
import com.github.adalpari.mvpretrofitjson.model.Result;
import com.karumi.marvelapiclient.MarvelApiConfig;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by plaza.a on 13/07/2016.
 * <p/>
 * Helper class for download Comic data from the Marvel API Client
 */
public class Downloader implements Callback<APIResponse> {

    // data needed by API
    private final static String PUBLIC_KEY = "6a7ed890b4b941a925202a5630d5b162";
    private final static String PRIVATE_KEY = "0f1d0fdf46a0bf32f962b0b9997233c0395cdf8e";
    private final static int CAPTAIN_AMERICA_ID = 1009220;
    private final static int LIMIT = 10;

    private final static String BASE_URL = "http://gateway.marvel.com:80";

    private MarvelApiConfig marvelApiConfig;

    private ComicsDownloadInterface mListener;

    private Retrofit retrofit;
    private ApiEndPointInterface marvelAPI;

    public Downloader(ComicsDownloadInterface listener) {
        this.marvelApiConfig = new MarvelApiConfig.Builder(PUBLIC_KEY, PRIVATE_KEY).debug().build();
        this.mListener = listener;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        marvelAPI = retrofit.create(ApiEndPointInterface.class);
    }

    /**
     * fetch comics from API
     *
     * @param offset from where to start
     */
    public void fetchComicsList(int offset) {
        Long timestamp = System.currentTimeMillis();
        String hash = md5(timestamp + PRIVATE_KEY + PUBLIC_KEY);

        if (hash != null) {
            Call<APIResponse> call = marvelAPI.getComics(CAPTAIN_AMERICA_ID, LIMIT, offset,
                    PUBLIC_KEY, timestamp, hash);
            call.enqueue(this);
        } else {
            mListener.onDownloadError();
        }
    }

    @Override
    public void onResponse(Call<APIResponse> call, retrofit2.Response<APIResponse> response) {
        APIResponse res = response.body();
        List<Comic> comicList = new ArrayList<>();

        for (Result comicResult : response.body().getData().getResults()) {
            Comic newComic = new Comic(comicResult.getTitle(), comicResult.getDescription(),
                    comicResult.getThumbnail().getPath() +
                            "/landscape_incredible" + "." + comicResult.getThumbnail().getExtension());

            for (Image comicImage : comicResult.getImages()) {
                newComic.addImageURL(comicImage.getPath() + "." + comicImage.getExtension());
            }

            comicList.add(newComic);
        }

        mListener.onComicsDownloaded(comicList);
    }

    @Override
    public void onFailure(Call<APIResponse> call, Throwable t) {
        mListener.onDownloadError();
    }

    private String md5(String s) {
        String output = null;

        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            output = hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return output;
    }
}
