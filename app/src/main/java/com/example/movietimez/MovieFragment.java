package com.example.movietimez;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.concurrent.TimeoutException;

public class MovieFragment extends Fragment {

    private Model selectedMovie = null;
    private TextView mTitleTextView;
    private TextView mGenreTextView;
    private TextView mLanguageTextView;
    private TextView mDurationTextView;
    private TextView mOverviewTextView;
    private ImageView mPicture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.selectedMovie = Constants.SELECTED_MOVIE;
        final View view = inflater.inflate(R.layout.movie_details, container, false);
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

    }
}
