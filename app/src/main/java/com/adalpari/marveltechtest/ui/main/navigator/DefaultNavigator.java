package com.adalpari.marveltechtest.ui.main.navigator;

import android.app.Activity;
import android.content.Intent;

import com.adalpari.marveltechtest.ui.comicdetails.view.DetailActivity;
import com.adalpari.marveltechtest.model.Comic;

/**
 * Created by plaza.a on 22/07/2016.
 */
public class DefaultNavigator implements Navigator {

    protected Activity activity;

    protected void launch(Intent intent) {
        activity.startActivity(intent);
    }

    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void goToDetailView(Comic comic) {
        int currentOrientation = getActivity().getResources().getConfiguration().orientation;

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(DetailActivity.COMIC_KEY, comic);
        launch(intent);
    }

    @Override
    public Activity getActivity() {
        return activity;
    }

    @Override
    public void finish() {
        activity.finish();
    }
}
