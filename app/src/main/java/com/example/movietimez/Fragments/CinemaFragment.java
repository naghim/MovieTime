package com.example.movietimez.Fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietimez.HelperClasses.Constants;
import com.example.movietimez.HelperClasses.DatabaseHelper;
import com.example.movietimez.Services.DownloadService;
import com.example.movietimez.Interfaces.HttpApiService;
import com.example.movietimez.Interfaces.OnItemClickListener;
import com.example.movietimez.Activities.MainActivity;
import com.example.movietimez.Models.Movie;
import com.example.movietimez.Models.MovieResponse;
import com.example.movietimez.Adapters.MovieAdapter;
import com.example.movietimez.R;
import com.example.movietimez.HelperClasses.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CinemaFragment extends Fragment implements ServiceConnection {

    private DownloadService s;
    private List<Movie> movieRecyclerList;
    private MovieAdapter mMovieAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mMovieRecylerView;
    private DatabaseHelper database = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homescreen_topten, container, false);

        ((MainActivity)getActivity()).startService(new Intent(getContext(), DownloadService.class));

        this.createRecycleList();
        this.buildRecycleView(view);

        return view;
    }

    public void createRecycleList(){
        this.movieRecyclerList = new ArrayList<>();
        this.getAllCinemaMovies();
    }

    private void getAllCinemaMovies()
    {
        database = new DatabaseHelper(getContext());
        Cursor cinemaMovies = database.getAllCinemaMovies();
        movieRecyclerList.clear();

        String movieId;
        if (cinemaMovies.getCount() == 0){
            Toast.makeText(getContext(), "List is empty...", Toast.LENGTH_SHORT).show();
            return;
        }else{
            while (cinemaMovies.moveToNext()){
                movieId = cinemaMovies.getString(1);
                getCinemaMovies(movieId);
            }
        }
    }

    private void getCinemaMovies(String movieId) {
        movieRecyclerList.clear();
        Log.d("IN CINEMA FRAGMENT", "IN cinema MOVIES -----");
        HttpApiService service = RetrofitClientInstance.getRetrofitInstance().create(HttpApiService.class);

        Call<MovieResponse> call = service.getCinemaList(Constants.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                movieRecyclerList = response.body().getResults();

                mMovieAdapter.setMovieRecyclerList(movieRecyclerList);
                mMovieAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // if the query doesn't succeed...
                //Toast.makeText(getContext(),"Couldn't load the movies.",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void buildRecycleView(View view) {
        mMovieRecylerView = view.findViewById(R.id.top_movies_recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mMovieRecylerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(getContext(), this.movieRecyclerList);
        mMovieRecylerView.setAdapter(mMovieAdapter);
        mMovieRecylerView.setLayoutManager(mLayoutManager);


        mMovieAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ((MainActivity) getActivity()).onMovieClicked(view, movieRecyclerList.get(position));
            }
        });
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        DownloadService.MyBinder b = (DownloadService.MyBinder) service;
        s = b.getService();
        Toast.makeText(getContext(), "Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        s = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent= new Intent(getContext(), DownloadService.class);
        ((MainActivity)getActivity()).bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).unbindService(this);
    }
}
