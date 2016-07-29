package com.github.adalpari.mvpretrofitjson.ui.main.navigator;

import android.app.Activity;

import com.github.adalpari.mvpretrofitjson.model.Comic;

/**
 * Created by plaza.a on 22/07/2016.
 */
public interface Navigator {

    void finish();

    Activity getActivity();

    void setActivity(Activity activity);

    void goToDetailView(Comic comic);
}
