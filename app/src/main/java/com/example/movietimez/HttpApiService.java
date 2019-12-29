package com.example.movietimez;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface HttpApiService {
//    @GET()
//    public Call<Model> getTopRatedMovies(@Url String url, @Query("page") String page);
//    @POST("listing.php")
//    public Call<Model> getTopRatedMovies(@Query("udid") String udid,
//                                         @Query("app_id") String app_id,
//                                         @Query("content") String content,
//                                         @Query("keyword") String keyword);

    @GET("movie/popular")
    Call<RetroMovie> getPopularMovies(@Query("api_key") String api_key);

}
