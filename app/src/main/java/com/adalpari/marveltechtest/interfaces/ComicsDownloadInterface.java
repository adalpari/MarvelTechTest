package com.adalpari.marveltechtest.interfaces;

import com.adalpari.marveltechtest.model.Comic;

import java.util.List;

/**
 * Created by plaza.a on 13/07/2016.
 *
 * Interface for communication between main thread and Downloader
 */
public interface ComicsDownloadInterface {
    /**
     * Called when the list of comics have been downlaoded
     * @param comics
     */
    public void onComicsDownloaded(List<Comic> comics);

    /**
     * Called when there is any error in server communication
     */
    public void onDownloadError();

    /**
     * Called when there is any error in response
     */
    public void onResponseError();
}
