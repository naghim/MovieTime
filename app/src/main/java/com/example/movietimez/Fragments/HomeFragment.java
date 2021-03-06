package com.example.movietimez.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietimez.HelperClasses.Constants;
import com.example.movietimez.HelperClasses.DatabaseHelper;
import com.example.movietimez.Interfaces.HttpApiService;
import com.example.movietimez.Activities.MainActivity;
import com.example.movietimez.Models.Movie;
import com.example.movietimez.Adapters.MovieAdapter;
import com.example.movietimez.Interfaces.OnItemClickListener;
import com.example.movietimez.R;
import com.example.movietimez.Models.MovieResponse;
import com.example.movietimez.HelperClasses.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private List<Movie> movieRecyclerList;
    private MovieAdapter mMovieAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mMovieRecylerView;
    private Context mContext;
    private DatabaseHelper database = null;
    private EditText mSearchBar;

    private String TAG = "HOMEFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.homescreen_topten, parent, false);
        mSearchBar = view.findViewById(R.id.toolbar);

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
        if (Constants.SELECTED_OPTION == Constants.SEARCH){
            this.getSearchedMovies();
        }
    }

    private void getSearchedMovies() {
        if (Constants.SEARCH_REQUEST.equals("")) return;
        movieRecyclerList.clear();
        Log.d(TAG, "IN SEARCHED MOVIES ----- query is : " + Constants.SEARCH_REQUEST);
        HttpApiService service = RetrofitClientInstance.getRetrofitInstance().create(HttpApiService.class);

        Call<MovieResponse> call = service.searchMovie(Constants.API_KEY, Constants.SEARCH_REQUEST );
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

    private void getFavourites()
    {
        database = new DatabaseHelper(getContext());
        Cursor favs = database.getAllFavourites(Constants.USERNAME);
        movieRecyclerList.clear();

        String movieId;
        if (favs.getCount() == 0){
            Toast.makeText(getContext(), "No favourites added so far... list is empty", Toast.LENGTH_SHORT).show();
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
        Call<Movie> call = service.getMovie(movieId, Constants.API_KEY );
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                movieRecyclerList.add(response.body());

                mMovieAdapter.setMovieRecyclerList(movieRecyclerList);
                mMovieAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                // if the query doesn't succeed...
                //Toast.makeText(getContext(),"Couldn't load the movies.",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getData(int page) {
        HttpApiService service = RetrofitClientInstance.getRetrofitInstance().create(HttpApiService.class);

        Call<MovieResponse> call = service.getPopularMovies(Constants.API_KEY, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieRecyclerList.clear();
                movieRecyclerList = response.body().getResults();
                Constants.PAGE = response.body().getPage();

                mMovieAdapter.setMovieRecyclerList(movieRecyclerList);
                Constants.RELATED_CONTENT = movieRecyclerList;
                mMovieAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // if the query doesn't succeed...
                Toast.makeText(getContext(),"Couldn't load the movies.",Toast.LENGTH_LONG).show();
            }
        });
    }

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
