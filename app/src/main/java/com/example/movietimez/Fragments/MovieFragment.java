package com.example.movietimez.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.movietimez.Constants;
import com.example.movietimez.DatabaseHelper;
import com.example.movietimez.Models.Model;
import com.example.movietimez.R;

public class MovieFragment extends Fragment {

    private Model selectedMovie = null;
    private TextView mTitleTextView;
    private TextView mGenreTextView;
    private TextView mLanguageTextView;
    private TextView mDurationTextView;
    private TextView mOverviewTextView;
    private ImageView mPicture;
    private Button mFavsButton;
    private DatabaseHelper database = null;


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
                database.addToFavourites(selectedMovie.id, Constants.USERNAME);
            }});


        if (Constants.SELECTED_OPTION == Constants.FAVS || Constants.SELECTED_OPTION == Constants.SEARCH)
        {
            mFavsButton.setVisibility(View.INVISIBLE);
        } else {
            mFavsButton.setVisibility(View.VISIBLE);
        }

    }
}
