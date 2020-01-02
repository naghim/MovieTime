package com.example.movietimez.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietimez.Constants;
import com.example.movietimez.DatabaseHelper;
import com.example.movietimez.Interfaces.HttpApiService;
import com.example.movietimez.MainActivity;
import com.example.movietimez.Models.Model;
import com.example.movietimez.MovieAdapter;
import com.example.movietimez.Interfaces.OnItemClickListener;
import com.example.movietimez.R;
import com.example.movietimez.Models.RetroMovie;
import com.example.movietimez.RetrofitClientInstance;

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
    private DatabaseHelper database = null;

    private String TAG = "HOMEFragment";

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
        if (Constants.SELECTED_OPTION == Constants.HOME){
            this.getData(1);
        }
        if (Constants.SELECTED_OPTION == Constants.FAVS){
            this.getFavourites();
        }
    }

    private void getFavourites()
    {
        database = new DatabaseHelper(getContext());
        Cursor favs = database.getAllFavourites(Constants.USERNAME);
        movieRecyclerList.clear();

        String movieId;
        if (favs.getCount() == 0){
            Toast.makeText(mContext, "No favourites added so far... list is empty", Toast.LENGTH_SHORT).show();
            return;
        }else{
            while (favs.moveToNext()){
                movieId = favs.getString(2);
                getFavouritesCall(movieId);
            }
        }
    }

    private void getFavouritesCall(String movieId) {
        Log.d(TAG, "IN getFavourites");
        HttpApiService service = RetrofitClientInstance.getRetrofitInstance().create(HttpApiService.class);

//        int movieId = 12; //Finding Nemo
        Call<Model> call = service.getMovie(movieId, Constants.API_KEY );
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                movieRecyclerList.add(response.body());

                mMovieAdapter.setMovieRecyclerList(movieRecyclerList);
                mMovieAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                // if the query doesn't succeed...
                //Toast.makeText(getContext(),"Couldn't load the movies.",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getData(int page) {
        HttpApiService service = RetrofitClientInstance.getRetrofitInstance().create(HttpApiService.class);

        Call<RetroMovie> call = service.getPopularMovies(Constants.API_KEY, page);
        call.enqueue(new Callback<RetroMovie>() {
            @Override
            public void onResponse(Call<RetroMovie> call, Response<RetroMovie> response) {
                movieRecyclerList.clear();
                movieRecyclerList = response.body().getResults();
                Constants.PAGE = response.body().getPage();

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

        mMovieRecylerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (Constants.SELECTED_OPTION == Constants.HOME)
                {
                    int total = mLayoutManager.getItemCount();
                    int lastVisibleItemCount = mLayoutManager.findLastVisibleItemPosition();

                    if (total > 0) {
                        if ((total-1) == lastVisibleItemCount) {
                            loadNextPageMovies();
                            mLayoutManager.scrollToPositionWithOffset(0, 0);
                        }
                    }
                }

            }
        });
    }

    private void loadNextPageMovies()
    {
            int nextPage = Constants.PAGE + 1;
            Constants.PAGE += 1;
            getData(nextPage);
    }

}
