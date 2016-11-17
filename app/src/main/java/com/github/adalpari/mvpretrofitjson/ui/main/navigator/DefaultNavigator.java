package com.github.adalpari.mvpretrofitjson.ui.main.navigator;

import android.app.Activity;
import android.content.Intent;

import com.github.adalpari.mvpretrofitjson.ui.comicdetails.view.DetailActivity;
import com.github.adalpari.mvpretrofitjson.model.Comic;

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
