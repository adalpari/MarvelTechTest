package com.adalpari.marveltechtest.interfaces;

import com.karumi.marvelapiclient.model.ComicsDto;
import com.karumi.marvelapiclient.model.MarvelResponse;

/**
 * Created by plaza.a on 13/07/2016.
 *
 * Interface for communication between main thread and Downloader
 */
public interface ComicsDownloadInterface {
    /**
     * Called when the list of comics have been downlaoded
     * @param response with all the content
     */
    public void onComicsDownloaded(MarvelResponse<ComicsDto> response);

    /**
     * Called when there is any error in server communication
     */
    public void onDownloadError();
}
