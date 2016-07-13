package com.adalpari.marveltechtest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.adalpari.marveltechtest.R;
import com.adalpari.marveltechtest.model.Comic;
import com.squareup.picasso.Picasso;

/**
 * Created by plaza.a on 13/07/2016.
 */
public class DetailActivity extends AppCompatActivity {

    // key for intent
    public final static String COMIC_KEY = "com.adalpari.marveltechtest.COMIC";

    // views
    private ImageView picture;
    private TextView title;
    private TextView description;

    // aux
    private Comic comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher);
        }

        // get views
        picture = (ImageView) findViewById(R.id.ivRandomPicture);
        title = (TextView) findViewById(R.id.tvTitle);
        description = (TextView) findViewById(R.id.tvDescription);

        // get comic to show from intent
        Intent mIntent = getIntent();
        comic = mIntent.getParcelableExtra(COMIC_KEY);

        // set info. Prevent HTML in description
        title.setText(comic.getTitle());
        description.setText(Html.fromHtml(comic.getDescription()));
    }

    public void onResume(){
        super.onResume();

        // Get a different random pic every time user open the activity. Surprise!
        Picasso.with(this)
                .load(comic.getRandomImage())
                .placeholder( R.drawable.progress_animation )
                .error(R.drawable.image_error)
                .fit()
                .into(picture);
    }
}
