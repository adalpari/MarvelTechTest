package com.github.adalpari.mvpretrofitjson.ui.main.presenter;

import com.github.adalpari.mvpretrofitjson.interfaces.ComicsDownloadInterface;
import com.github.adalpari.mvpretrofitjson.model.Comic;
import com.github.adalpari.mvpretrofitjson.ui.main.navigator.Navigator;
import com.github.adalpari.mvpretrofitjson.utils.Downloader;

import java.util.List;

/**
 * Created by plaza.a on 22/07/2016.
 */
public class MainActivityPresenter implements ComicsDownloadInterface {
    private IMainView view;
    private Navigator mNavigator;
    private Downloader mDownloader;

    public MainActivityPresenter(IMainView view, Navigator navigator) {
        this.view = view;
        mDownloader = new Downloader(this);
        mNavigator = navigator;
    }

    public void onComicClicked(Comic comic) {
        mNavigator.goToDetailView(comic);
    }

    public void fetchComicList(int offset) {
        mDownloader.fetchComicsList(offset);
    }

    @Override
    public void onComicsDownloaded(List<Comic> comics) {
        view.onComicsDownloaded(comics);
    }

    @Override
    public void onDownloadError() {
        view.onDownloadError();
    }

    @Override
    public void onResponseError() {
        view.onResponseError();
    }
}
