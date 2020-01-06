package com.example.movietimez.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movietimez.HelperClasses.Constants;
import com.example.movietimez.HelperClasses.DatabaseHelper;
import com.example.movietimez.Interfaces.HttpApiService;
import com.example.movietimez.Interfaces.OnItemClickListener;
import com.example.movietimez.Activities.MainActivity;
import com.example.movietimez.Models.Movie;
import com.example.movietimez.Models.Video;
import com.example.movietimez.Models.VideoResponse;
import com.example.movietimez.R;
import com.example.movietimez.Adapters.RelatedContentAdapter;
import com.example.movietimez.HelperClasses.RetrofitClientInstance;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private Movie selectedMovie = null;
    private TextView mTitleTextView;
    private TextView mGenreTextView;
    private TextView mLanguageTextView;
    private TextView mDurationTextView;
    private TextView mOverviewTextView;
    private ImageView mPicture;
    private Button mFavsButton;
    private DatabaseHelper database = null;

    private YouTubePlayerFragment playerFragment;
    private YouTubePlayer mPlayer;

    private List<Movie> movieRecyclerList;
    private RelatedContentAdapter mMovieAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mMovieRecylerView;

    List<Video> videoList = new ArrayList<Video>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.selectedMovie = Constants.SELECTED_MOVIE;
        final View view = inflater.inflate(R.layout.movie_details, container, false);
        database = new DatabaseHelper(getContext());

        buildDetailedView(view);

        return view;
    }

    private void buildDetailedView(View view) {
        this.mTitleTextView = view.findViewById(R.id.titleTextView);
        mTitleTextView.setText(this.selectedMovie.getTitle());

        this.mGenreTextView = view.findViewById(R.id.genresTextView);
        mGenreTextView.setText(selectedMovie.getReleaseDate());

        this.mLanguageTextView = view.findViewById(R.id.languageTextView);
        mLanguageTextView.setText(this.selectedMovie.getOriginalLanguage());

        float voteAverage = this.selectedMovie.getVoteAverage();
        String voteAverageString = String.valueOf(voteAverage);
        this.mDurationTextView = view.findViewById(R.id.durationTextView);
        mDurationTextView.setText(voteAverageString);

        this.mOverviewTextView = view.findViewById(R.id.overviewTextView);
        mOverviewTextView.setText(this.selectedMovie.getOverview());

        this.mPicture = view.findViewById(R.id.imageView);
        Glide.with(getContext()).load(selectedMovie.getPosterPath()).into(mPicture);

        this.mFavsButton = view.findViewById(R.id.favsButton);
        mFavsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Added to favourites...", Toast.LENGTH_SHORT).show();
                database.addToFavourites(selectedMovie.id, Constants.USERNAME);
            }});


        if (Constants.SELECTED_OPTION == Constants.FAVS)
        {
            mFavsButton.setVisibility(View.INVISIBLE);
        } else {
            mFavsButton.setVisibility(View.VISIBLE);
        }

        getVideo();
        this.createRecycleList();
        this.buildRecycleView(view);

    }

    private void buildRecycleView(View view) {
        mMovieRecylerView = view.findViewById(R.id.related_content);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        mMovieRecylerView.setHasFixedSize(true);

        mMovieAdapter = new RelatedContentAdapter(getContext(), this.movieRecyclerList);
        mMovieRecylerView.setAdapter(mMovieAdapter);
        mMovieRecylerView.setLayoutManager(mLayoutManager);


        mMovieAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view , int position) {
                ((MainActivity)getActivity()).onMovieClicked(view, movieRecyclerList.get(position));
            }
        });
    }

    private void createRecycleList() {
        movieRecyclerList = Constants.RELATED_CONTENT;
    }

    private void getVideo() {
        HttpApiService service = RetrofitClientInstance.getRetrofitInstance().create(HttpApiService.class);

        Call<VideoResponse> call = service.getVideos(Constants.SELECTED_MOVIE.getId(), Constants.API_KEY);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                videoList.clear();
                videoList = response.body().getResults();

                if (videoList.isEmpty()) return;

                YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

                // it shows an error, but it can run... Youtube api wasn't updated with the new androidx library...
                transaction.add(R.id.youtube_player_fragment, youTubePlayerFragment).commit();


                youTubePlayerFragment.initialize(Constants.YT_API_KEY, new YouTubePlayer.OnInitializedListener() {

                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                        if (!wasRestored) {
                            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                            player.loadVideo(videoList.get(0).getKey());
                            player.play();
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                        // YouTube error
                        String errorMessage = error.toString();
                        Log.d("errorMessage:", errorMessage);
                    }
                });

            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                // if the query doesn't succeed...
                Toast.makeText(getContext(), "Couldn't load the movies.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
