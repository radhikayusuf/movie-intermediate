package yusuf.radhika.id.movie_intermediate.main;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yusuf.radhika.id.movie_intermediate.R;
import yusuf.radhika.id.movie_intermediate.data.ApiClient;
import yusuf.radhika.id.movie_intermediate.data.dao.MovieResponseDao;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerMain;
    private MainAdapter mAdapter;
    private List<MainDao> mData = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new MainAdapter(mData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerMain = (RecyclerView) findViewById(R.id.recyclerMain);
        mRecyclerMain.setAdapter(mAdapter);
        mRecyclerMain.setLayoutManager(gridLayoutManager);


        ApiClient.service().getMovieList("983e2812b719e865d53ccf59c73d7624")
                .enqueue(new Callback<MovieResponseDao>() {
                    @Override
                    public void onResponse(Call<MovieResponseDao> call, Response<MovieResponseDao> response) {
                        if (response.isSuccessful()) {

                            for (MovieResponseDao.MovieData data : response.body().getResults()) {
                                mData.add(new MainDao(data.getTitle(), "https://image.tmdb.org/t/p/w185/" + data.getPoster_path()));
                            }

                            mAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponseDao> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                mData.add(new MainDao("Satu", "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg"));
//                mData.add(new MainDao("Dua", "https://lh3.googleusercontent.com/AkTbUA_l8OICZ7xKGMY3-w0_kgCIRz_yEXMOYgHokTR9P_vszUvdp4udMP_o4VaCDkg=w300"));
//                mData.add(new MainDao("Tiga", "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg"));
//                mData.add(new MainDao("Empat", "https://lh3.googleusercontent.com/AkTbUA_l8OICZ7xKGMY3-w0_kgCIRz_yEXMOYgHokTR9P_vszUvdp4udMP_o4VaCDkg=w300"));
//                mData.add(new MainDao("Satu", "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg"));
//                mData.add(new MainDao("Dua", "https://lh3.googleusercontent.com/AkTbUA_l8OICZ7xKGMY3-w0_kgCIRz_yEXMOYgHokTR9P_vszUvdp4udMP_o4VaCDkg=w300"));
//                mData.add(new MainDao("Tiga", "http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg"));
//                mData.add(new MainDao("Empat", "https://lh3.googleusercontent.com/AkTbUA_l8OICZ7xKGMY3-w0_kgCIRz_yEXMOYgHokTR9P_vszUvdp4udMP_o4VaCDkg=w300"));
//
//                mAdapter.notifyDataSetChanged();
//            }
//        }, 5000);

        Toast.makeText(this, "Loading data 5 detik ...", Toast.LENGTH_SHORT).show();

    }



}
