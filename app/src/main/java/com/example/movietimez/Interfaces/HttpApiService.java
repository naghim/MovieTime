package com.example.movietimez.Interfaces;

import com.example.movietimez.Models.Model;
import com.example.movietimez.Models.RetroMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HttpApiService {
//    @GET()
//    public Call<Model> getTopRatedMovies(@Url String url, @Query("page") String page);
//    @POST("listing.php")
//    public Call<Model> getTopRatedMovies(@Query("udid") String udid,
//                                         @Query("app_id") String app_id,
//                                         @Query("content") String content,
//                                         @Query("keyword") String keyword);

    @GET("movie/popular")
    Call<RetroMovie> getPopularMovies(@Query("api_key") String api_key, @Query("page") int page);

    //@GET("movie")
//    Call<RetroMovie> getFavourites(@Query("") String movieId,
//                                   @Query("api_key") String api_key,
//                                   @Query("language") String lang,
//                                   @Query("external_source") String external);

//    Call<RetroMovie> getFavourites(@Path() String movieId,
//                                   @Query("api_key") String api_key,
//                                   @Query("language") String lang,
//                                   @Query("external_source") String external);
    @GET("/3/movie/{id}")
    Call<Model> getMovie(@Path("id") String id, @Query("api_key") String api_key);
}
