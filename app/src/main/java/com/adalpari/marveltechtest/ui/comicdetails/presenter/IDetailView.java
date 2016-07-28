package com.adalpari.marveltechtest.ui.comicdetails.presenter;

/**
 * Created by plaza.a on 22/07/2016.
 */
public interface IDetailView {
    void drawComic(String imageURL, String title, String description);

    void refreshImage(String imageURL);
}
