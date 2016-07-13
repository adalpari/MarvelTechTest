package com.adalpari.marveltechtest.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adalpari.marveltechtest.R;
import com.adalpari.marveltechtest.interfaces.ClickInterface;
import com.adalpari.marveltechtest.interfaces.ComicsDownloadInterface;
import com.adalpari.marveltechtest.listadapters.ComicAdapter;
import com.adalpari.marveltechtest.listadapters.RowDivider;
import com.adalpari.marveltechtest.model.Comic;
import com.adalpari.marveltechtest.utils.Downloader;
import com.karumi.marvelapiclient.model.ComicDto;
import com.karumi.marvelapiclient.model.ComicsDto;
import com.karumi.marvelapiclient.model.MarvelImage;
import com.karumi.marvelapiclient.model.MarvelResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by plaza.a on 13/07/2016.
 */
public class MainActivity extends AppCompatActivity implements ComicsDownloadInterface {

    // views
    private RecyclerView recyclerView;
    private ProgressBar spinner;

    //comics
    private List<Comic> comicList;
    private ComicAdapter mAdapter;

    // variables for handle "scroll to bottom" action and offset of list
    private boolean loading = false;
    private int pastVisiblesItems;
    private int visibleItemCount;
    private int totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher);
        }

        // find views
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        spinner = (ProgressBar)findViewById(R.id.progressBar);

        //prepare list, recyclerview and adapters
        comicList = new ArrayList<>();
        mAdapter = new ComicAdapter(comicList, this);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RowDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    //check if downloader is already loading
                    if (!loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            fetchComicData();
                        }
                    }
                }
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickInterface() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent mIntent = new Intent(getBaseContext(), DetailActivity.class);
                mIntent.putExtra(DetailActivity.COMIC_KEY, comicList.get(position));
                startActivity(mIntent);
            }
        }));


        fetchComicData();
    }

    /**
     * Fech comic data ussing Downloader class
     */
    private void fetchComicData() {
        spinner.setVisibility(View.VISIBLE);
        loading = true;
        Downloader mDownloader = new Downloader(this);
        mDownloader.fetchComicsList(totalItemCount);
    }

    @Override
    public void onComicsDownloaded(MarvelResponse<ComicsDto> response) {
        if (response != null && response.getCode() == 200){

            // add all the relevant info from downlaoded commics
            for (ComicDto comic : response.getResponse().getComics()){
                Comic insComic = new Comic(comic.getTitle(), comic.getDescription(), comic.getThumbnail().getImageUrl(MarvelImage.Size.LANDSCAPE_INCREDIBLE));
                for (MarvelImage img : comic.getImages()){
                    insComic.addImageURL(img.getImageUrl(MarvelImage.Size.FULLSIZE));
                }
                comicList.add(insComic);
            }

            mAdapter.notifyDataSetChanged();
        } else {
            Toast error = Toast.makeText(getBaseContext(), getResources().getString(R.string.fetch_ko), Toast.LENGTH_LONG);
            error.setGravity(Gravity.CENTER, 0, 0);
            error.show();
        }

        loading = false;
        spinner.setVisibility(View.GONE);
    }

    @Override
    public void onDownloadError() {
        spinner.setVisibility(View.GONE);
        loading = false;
        Toast error = Toast.makeText(getBaseContext(), getResources().getString(R.string.fetch_error), Toast.LENGTH_LONG);
        error.setGravity(Gravity.CENTER, 0, 0);
        error.show();
    }


    /**
     * Class for handle RecyclerView OnItemClick
     */
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickInterface clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickInterface clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {}
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onItemClicked(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}