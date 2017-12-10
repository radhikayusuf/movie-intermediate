package yusuf.radhika.id.movie_intermediate.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author radhikayusuf.
 */

public class ApiClient {

    public static ApiRequestInterface service(){

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build();

        return retrofit.create(ApiRequestInterface.class);
    }

}
