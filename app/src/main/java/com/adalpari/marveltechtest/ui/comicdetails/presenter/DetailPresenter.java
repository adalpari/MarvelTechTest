package com.adalpari.marveltechtest.ui.comicdetails.presenter;

import com.adalpari.marveltechtest.model.Comic;

/**
 * Created by plaza.a on 22/07/2016.
 */
public class DetailPresenter {
    private Comic comic;
    IDetailView view;

    public DetailPresenter(IDetailView view, Comic comic) {
        this.view = view;
        this.comic = comic;
    }

    public void drawComic(){
        view.drawComic(comic.getRandomImage(), comic.getTitle(), comic.getDescription());
    }

    public void randomImage(){
        view.refreshImage(comic.getRandomImage());
    }
}
