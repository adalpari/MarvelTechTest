package com.adalpari.marveltechtest.ui.main.presenter;

import com.adalpari.marveltechtest.interfaces.ComicsDownloadInterface;
import com.adalpari.marveltechtest.model.Comic;
import com.adalpari.marveltechtest.ui.main.navigator.Navigator;
import com.adalpari.marveltechtest.utils.Downloader;

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
