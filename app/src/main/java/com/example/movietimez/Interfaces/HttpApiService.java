package com.example.movietimez.Interfaces;

import com.example.movietimez.Models.Model;
import com.example.movietimez.Models.RetroMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HttpApiService {
    @GET("movie/popular")
    Call<RetroMovie> getPopularMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("/3/movie/{id}")
    Call<Model> getMovie(@Path("id") String id, @Query("api_key") String api_key);

    @GET("/3/search/movie")
    Call<RetroMovie> searchMovie(@Query("api_key") String api_key, @Query("query") String query);
}
