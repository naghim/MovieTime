package com.example.movietimez;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private List<Model> movieRecyclerList;
    private MovieAdapter mMovieAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mMovieRecylerView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.homescreen_topten, parent, false);

        this.createRecycleList();
        this.mContext = getContext();
        this.buildRecycleView(view);

        return view;
    }

    // Creates the RecylcerList
    public void createRecycleList(){
        this.movieRecyclerList = new ArrayList<>();
        this.getData();
    }

    private void getData() {
        HttpApiService service = RetrofitClientInstance.getRetrofitInstance().create(HttpApiService.class);

        Call<RetroMovie> call = service.getPopularMovies(Constants.API_KEY);
        call.enqueue(new Callback<RetroMovie>() {
            @Override
            public void onResponse(Call<RetroMovie> call, Response<RetroMovie> response) {
                movieRecyclerList.clear();
                movieRecyclerList = response.body().getResults();

                mMovieAdapter.setMovieRecyclerList(movieRecyclerList);
                mMovieAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<RetroMovie> call, Throwable t) {
                // if the query doesn't succeed...
                Toast.makeText(getContext(),"Couldn't load the movies.",Toast.LENGTH_LONG).show();
            }
        });
    }

    // Builds RecyclerView: Sets layouts and adapter. Sends list with data to the adapter.
    public void buildRecycleView(View view){
        mMovieRecylerView = view.findViewById(R.id.top_movies_recycler_view);
        mLayoutManager = new LinearLayoutManager(this.mContext);
        mMovieRecylerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this.mContext, this.movieRecyclerList);
        mMovieRecylerView.setAdapter(mMovieAdapter);
        mMovieRecylerView.setLayoutManager(mLayoutManager);

        mMovieAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view , int position) {
                ((MainActivity)getActivity()).onMovieClicked(view, movieRecyclerList.get(position));
            }
        });
    }
}
