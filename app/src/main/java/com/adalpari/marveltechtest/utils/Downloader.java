package com.adalpari.marveltechtest.utils;

import android.os.AsyncTask;

import com.adalpari.marveltechtest.interfaces.ComicsDownloadInterface;
import com.adalpari.marveltechtest.model.Comic;
import com.karumi.marvelapiclient.ComicApiClient;
import com.karumi.marvelapiclient.MarvelApiConfig;
import com.karumi.marvelapiclient.MarvelApiException;
import com.karumi.marvelapiclient.model.ComicDto;
import com.karumi.marvelapiclient.model.ComicsDto;
import com.karumi.marvelapiclient.model.ComicsQuery;
import com.karumi.marvelapiclient.model.MarvelImage;
import com.karumi.marvelapiclient.model.MarvelResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by plaza.a on 13/07/2016.
 * <p/>
 * Helper class for download Comic data from the Marvel API Client
 */
public class Downloader {

    // data needed by API
    private final static String PUBLIC_KEY = "6a7ed890b4b941a925202a5630d5b162";
    private final static String PRIVATE_KEY = "0f1d0fdf46a0bf32f962b0b9997233c0395cdf8e";
    private final static int CAPTAIN_AMERICA_ID = 1009220;

    private MarvelApiConfig marvelApiConfig;

    private ComicsDownloadInterface mListener;

    // set the limit for each request
    private final static int LIMIT = 10;

    public Downloader(ComicsDownloadInterface listener) {
        this.marvelApiConfig = new MarvelApiConfig.Builder(PUBLIC_KEY, PRIVATE_KEY).debug().build();
        this.mListener = listener;
    }

    /**
     * fetch comics from API
     *
     * @param offset from where to start
     */
    public void fetchComicsList(int offset) {
        new DownloadComicsTask().execute(offset);
    }


    /**
     * AsyncTask class
     * Used to call the API through karumi library
     */
    private class DownloadComicsTask extends AsyncTask<Integer, Void, MarvelResponse<ComicsDto>> {

        protected MarvelResponse<ComicsDto> doInBackground(Integer... params) {
            MarvelResponse<ComicsDto> all = null;

            if (params.length == 1) {

                int offset = params[0];

                try {
                    ComicApiClient comicApiClient = new ComicApiClient(marvelApiConfig);
                    ComicsQuery query = ComicsQuery.Builder.create().addCharacter(CAPTAIN_AMERICA_ID).withOffset(offset).withLimit(LIMIT).build();
                    all = comicApiClient.getAll(query);
                } catch (MarvelApiException e) {
                    e.printStackTrace();
                    all = null;
                }
            }

            return all;
        }


        protected void onPostExecute(MarvelResponse<ComicsDto> result) {
            if (result != null) {
                if (result.getCode() == 200) {
                    List<Comic> comicList = new ArrayList<Comic>();

                    // add all the relevant info from downlaoded commics
                    for (ComicDto comic : result.getResponse().getComics()) {
                        Comic insComic = new Comic(comic.getTitle(), comic.getDescription(), comic.getThumbnail().getImageUrl(MarvelImage.Size.LANDSCAPE_INCREDIBLE));
                        for (MarvelImage img : comic.getImages()) {
                            insComic.addImageURL(img.getImageUrl(MarvelImage.Size.FULLSIZE));
                        }
                        comicList.add(insComic);
                    }
                    mListener.onComicsDownloaded(comicList);

                } else {
                    mListener.onResponseError();
                }

            } else {
                mListener.onDownloadError();
            }
        }

    }
}
