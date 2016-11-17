package com.github.adalpari.mvpretrofitjson.ui.main.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.adalpari.mvpretrofitjson.R;
import com.github.adalpari.mvpretrofitjson.interfaces.ClickInterface;
import com.github.adalpari.mvpretrofitjson.model.Comic;
import com.github.adalpari.mvpretrofitjson.ui.main.navigator.DefaultNavigator;
import com.github.adalpari.mvpretrofitjson.ui.main.navigator.Navigator;
import com.github.adalpari.mvpretrofitjson.ui.main.presenter.IMainView;
import com.github.adalpari.mvpretrofitjson.ui.main.presenter.MainActivityPresenter;
import com.github.adalpari.mvpretrofitjson.ui.main.view.listadapters.ComicAdapter;
import com.github.adalpari.mvpretrofitjson.ui.main.view.listadapters.RowDivider;
import com.github.adalpari.mvpretrofitjson.utils.Downloader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainView {

    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar spinner;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    private ComicAdapter mAdapter;
    private MainActivityPresenter mPresenter;

    private boolean loading = false;
    private int pastVisiblesItems;
    private int visibleItemCount;
    private int totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Navigator mNavigator = new DefaultNavigator();
        mNavigator.setActivity(this);
        Downloader mDownloader = new Downloader();
        mPresenter = new MainActivityPresenter(this, mNavigator, mDownloader);
        mPresenter.onAttach();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher);
        }

        mAdapter = new ComicAdapter(null, this);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RowDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                //check if downloader is already loading
                if (!loading
                    && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        fetchComicData();
                    }
                }
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickInterface() {
            @Override
            public void onItemClicked(View view, int position) {
                mPresenter.onComicClicked(mAdapter.getComic(position));
            }
        }));

        fetchComicData();
    }

    private void fetchComicData() {
        spinner.setVisibility(View.VISIBLE);
        loading = true;
        mPresenter.fetchComicList(totalItemCount);
    }

    @Override
    public void onComicsDownloaded(List<Comic> comics) {

        mAdapter.addComics(comics);
        loading = false;
        spinner.setVisibility(View.GONE);
    }

    @Override
    public void onDownloadError() {
        spinner.setVisibility(View.GONE);
        loading = false;
        showSnackBar(getResources().getString(R.string.fetch_error), true);
    }

    @Override
    public void onResponseError() {
        spinner.setVisibility(View.GONE);
        loading = false;
        showSnackBar(getResources().getString(R.string.fetch_ko), false);
    }

    private void showSnackBar(String text, boolean retryFetch) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_LONG);

        if (retryFetch) {
            snackbar.setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fetchComicData();
                }
            });
        }

        snackbar.show();
    }


    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickInterface clickListener;

        public RecyclerTouchListener(Context context,
                                     final RecyclerView recyclerView,
                                     final ClickInterface clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                }
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