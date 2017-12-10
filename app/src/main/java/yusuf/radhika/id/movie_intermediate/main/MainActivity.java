package yusuf.radhika.id.movie_intermediate.main;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yusuf.radhika.id.movie_intermediate.R;
import yusuf.radhika.id.movie_intermediate.data.ApiClient;
import yusuf.radhika.id.movie_intermediate.data.dao.MovieResponseDao;
import yusuf.radhika.id.movie_intermediate.data.offline.MovieContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerMain;
    private SwipeRefreshLayout mRefreshLayout;
    private Toolbar mToolbar;

    private MainAdapter mAdapter;
    private List<MainDao> mData = new ArrayList<>();

    private String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(0, null, this);


        mAdapter = new MainAdapter(mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerMain = (RecyclerView) findViewById(R.id.recyclerMain);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeMain);
        mToolbar = (Toolbar) findViewById(R.id.toolbarMain);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.my_film);

        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerMain.setAdapter(mAdapter);
        mRecyclerMain.setLayoutManager(gridLayoutManager);


        getDataMovie();
    }


    /**
     * method untuk mengambil data dari API
     */
    private void getDataMovie() {
        mRefreshLayout.setRefreshing(true);

        ApiClient.service().getMovieList("983e2812b719e865d53ccf59c73d7624")
                .enqueue(new Callback<MovieResponseDao>() {
                    @Override
                    public void onResponse(Call<MovieResponseDao> call, Response<MovieResponseDao> response) {
                        if (response.isSuccessful()) {
                            mRefreshLayout.setRefreshing(false);

                            Uri deleteUri = MovieContract.MovieEntry.CONTENT_URI;
                            getContentResolver().delete(deleteUri, null, null);

                            for (MovieResponseDao.MovieData data : response.body().getResults()) {
                                ContentValues contentValues = new ContentValues();

                                contentValues.put(MovieContract.MovieEntry.COLUMN_FAVORITE_IDS, data.getId());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, data.getTitle());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_ORI_TITLE, data.getOriginal_title());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, data.getVote_count());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_VIDEO, data.isVideo() ? 1 : 0);
                                contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVG, data.getVote_average());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, data.getPopularity());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, data.getPoster_path());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANG, data.getOriginal_language());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_GENRE, "");
                                contentValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, data.getBackdrop_path());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_ADULT, data.isAdult() ? 1 : 0);
                                contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, data.getOverview());
                                contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, data.getRelease_date());

                                Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

                                if (uri != null) {
                                    Log.d("onResponse: ", "INSERT DATA SUCCESS!");
                                }
                            }
                            getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
                        } else {
                            mRefreshLayout.setRefreshing(false);
                            Toast.makeText(MainActivity.this, "Internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponseDao> call, Throwable t) {
                        mRefreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }



    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mMovieData = null;

            @Override
            protected void onStartLoading() {
                if (mMovieData != null) {
                    deliverResult(mMovieData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            MovieContract.MovieEntry._ID);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.\n" + e.getMessage());
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mMovieData = data;
                super.deliverResult(data);
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("onLoadFinished: ", String.valueOf(data.getCount()));
        mData.clear();

        for (int i = 0; i < data.getCount(); i++) {
            data.moveToPosition(i);

            mData.add(new MainDao(
                    data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)),
                    data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)),
                    "https://image.tmdb.org/t/p/w185/" + data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH)),
                    "https://image.tmdb.org/t/p/w185/" + data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH)),
                    data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE))
            ));

        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onRefresh() {
        getDataMovie();
    }
}
