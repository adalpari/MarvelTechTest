package com.github.adalpari.mvpretrofitjson.interfaces;

import com.github.adalpari.mvpretrofitjson.model.Comic;

import java.util.List;

/**
 * Created by plaza.a on 13/07/2016.
 * <p/>
 * Interface for communication between main thread and Downloader
 */
public interface ComicsDownloadInterface {
    /**
     * Called when the list of comics have been downlaoded
     *
     * @param comics
     */
    void onComicsDownloaded(List<Comic> comics);

    /**
     * Called when there is any error in server communication
     */
    void onDownloadError();

    /**
     * Called when there is any error in response
     */
    void onResponseError();
}
