package com.example.movietimez.Interfaces;

import com.example.movietimez.Models.Movie;
import com.example.movietimez.Models.MovieResponse;
import com.example.movietimez.Models.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HttpApiService {
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("/3/movie/{id}")
    Call<Movie> getMovie(@Path("id") String id, @Query("api_key") String api_key);

    @GET("/3/search/movie")
    Call<MovieResponse> searchMovie(@Query("api_key") String api_key, @Query("query") String query);

    @GET("/3/movie/{id}/videos")
    Call<VideoResponse> getVideos(@Path("id") int id, @Query("api_key") String api_key);

    @GET("/3/movie/now_playing")
    Call<MovieResponse> getCinemaList(@Query("api_key") String api_key);
}
