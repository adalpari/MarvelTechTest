package com.adalpari.marveltechtest.ui.main.presenter;

import com.adalpari.marveltechtest.model.Comic;

import java.util.List;

/**
 * Created by plaza.a on 22/07/2016.
 */
public interface IMainView {
    public void onComicsDownloaded(List<Comic>comics);
    public void onDownloadError();
    public void onResponseError();
}
