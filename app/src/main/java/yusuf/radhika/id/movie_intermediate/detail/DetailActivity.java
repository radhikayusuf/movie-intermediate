package yusuf.radhika.id.movie_intermediate.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import yusuf.radhika.id.movie_intermediate.R;
import yusuf.radhika.id.movie_intermediate.main.MainDao;

public class DetailActivity extends AppCompatActivity {

    private MainDao mData;

    private TextView textTitle, textDesc, textRelease;
    private ImageView imagePoster, imageBackground;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mData = getIntent().getParcelableExtra("dataMovie");


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(mData.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        textTitle = (TextView) findViewById(R.id.textTitle);
        textRelease = (TextView) findViewById(R.id.textReleaseDate);
        textDesc = (TextView) findViewById(R.id.textDescription);
        imagePoster = (ImageView) findViewById(R.id.imagePoster);
        imageBackground = (ImageView) findViewById(R.id.imageToolbar);

        textTitle.setText(mData.getTitle());
        textRelease.setText("Release date " + mData.getReleaseDate());
        textDesc.setText(mData.getDescription());

        Picasso.with(this)
                .load(mData.getImageUrl())
                .into(imagePoster);

        Picasso.with(this)
                .load(mData.getImageBackground())
                .into(imageBackground);
    }
}
