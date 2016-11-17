package com.github.adalpari.mvpretrofitjson.ui.comicdetails.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.adalpari.mvpretrofitjson.R;
import com.github.adalpari.mvpretrofitjson.model.Comic;
import com.github.adalpari.mvpretrofitjson.ui.comicdetails.presenter.DetailPresenter;
import com.github.adalpari.mvpretrofitjson.ui.comicdetails.presenter.IDetailView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by plaza.a on 13/07/2016.
 */
public class DetailActivity extends AppCompatActivity implements IDetailView {

    public final static String COMIC_KEY = "com.adalpari.marveltechtest.COMIC";

    @BindView(R.id.ivRandomPicture)
    ImageView ivPicture;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDescription)
    TextView tvDescription;

    private DetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher);
        }

        Intent mIntent = getIntent();
        Comic comic = mIntent.getParcelableExtra(COMIC_KEY);
        mPresenter = new DetailPresenter(this, comic);
    }

    public void onResume() {
        super.onResume();
        mPresenter.drawComic();
    }

    @Override
    public void drawComic(String imageURL, String title, String description) {
        refreshImage(imageURL);
        tvTitle.setText(title);
        tvDescription.setText(Html.fromHtml(description));
    }

    @Override
    public void refreshImage(String imageURL) {
        Picasso.with(this)
                .load(imageURL)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.image_error)
                .fit()
                .into(ivPicture);
    }
}
