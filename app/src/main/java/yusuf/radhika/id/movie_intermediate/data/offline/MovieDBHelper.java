package yusuf.radhika.id.movie_intermediate.data.offline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Radhika Yusuf on 12/07/17.
 */

class MovieDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movieDB.db";

    private static final int VERSION = 2;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + "("+
                MovieContract.MovieEntry._ID                    + " INTEGER PRIMARY KEY, "+
                MovieContract.MovieEntry.COLUMN_FAVORITE_IDS    + " INTEGER NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_TITLE           + " TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_ORI_TITLE       + " TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_OVERVIEW        + " TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_POSTER_PATH     + " TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_BACKDROP_PATH   + " TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_ORIGINAL_LANG   + " TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_ADULT           + " INTEGER NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_VIDEO           + " INTEGER NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE    + " TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_GENRE           + " TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_VOTE_COUNT      + " INTEGER NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_VOTE_AVG        + " REAL NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_POPULARITY      + " REAL NOT NULL); ";


        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
