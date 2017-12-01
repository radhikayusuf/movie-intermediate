package yusuf.radhika.id.movie_intermediate.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import yusuf.radhika.id.movie_intermediate.data.dao.MovieResponseDao;

/**
 * @author radhikayusuf.
 */

public interface ApiRequestInterface {

    @GET("movie/popular")
    Call<MovieResponseDao> getMovieList(@Query("api_key") String api_key);

}
