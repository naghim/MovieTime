package com.example.movietimez.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.movietimez.HelperClasses.Constants;
import com.example.movietimez.HelperClasses.DatabaseHelper;
import com.example.movietimez.Interfaces.HttpApiService;
import com.example.movietimez.Models.Movie;
import com.example.movietimez.Models.MovieResponse;
import com.example.movietimez.HelperClasses.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadService extends Service {
    private final IBinder mBinder = new MyBinder();
    private List<Movie> movieRecyclerList = new ArrayList<Movie>();
    private int counter = 1;
    private DatabaseHelper database = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        getCinemaMovies();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.d("SERVICE ", "running method **************");
                getCinemaMovies();
            }
        }, 1000, Constants.INTERVAL);

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        public DownloadService getService() {
            return DownloadService.this;
        }
    }

    private void getCinemaMovies() {
        Log.d("IN CINEMA FRAGMENT", "IN cinema MOVIES ----- query is : " + Constants.SEARCH_REQUEST);
        HttpApiService service = RetrofitClientInstance.getRetrofitInstance().create(HttpApiService.class);

        Call<MovieResponse> call = service.getCinemaList(Constants.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                movieRecyclerList = response.body().getResults();
                saveMoviesInDB(movieRecyclerList);
            }

            private void saveMoviesInDB(List<Movie> movieRecyclerList) {
                database = new DatabaseHelper(getBaseContext());
                database.saveMovies(movieRecyclerList);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // if the query doesn't succeed...
                //Toast.makeText(getContext(),"Couldn't load the movies.",Toast.LENGTH_LONG).show();
            }
        });
    }


}
