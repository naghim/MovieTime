package com.example.movietimez.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.movietimez.Fragments.ChangePassword;
import com.example.movietimez.Fragments.CinemaFragment;
import com.example.movietimez.Fragments.HomeFragment;
import com.example.movietimez.Fragments.LoginFragment;
import com.example.movietimez.Fragments.MovieFragment;
import com.example.movietimez.Fragments.ProfileFragment;
import com.example.movietimez.HelperClasses.Constants;
import com.example.movietimez.Models.Movie;
import com.example.movietimez.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;
    public BottomNavigationView bottomNav;

    @Override
    /**
     * OnCreate.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place,
                    new LoginFragment()).commit();
        }
    }

    /**
     * For bottom navigation.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                    case R.id.nav_favourites:
                        selectedFragment = new HomeFragment();
                        Constants.SELECTED_OPTION = Constants.FAVS;
                        break;
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        Constants.SELECTED_OPTION = Constants.HOME;
                        break;
                    case R.id.nav_nowplaying:
                        selectedFragment = new CinemaFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place,
                        selectedFragment).commit();

                return true;
            };

    /**
     * After login, redirect user to home screen.
     */
    public void afterLogin() {
        bottomNav.setVisibility(View.VISIBLE);
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.fragment_place, new HomeFragment());
        ft.commit();
    }

    /**
     * On movie clicked: redirect user to movie details.
     * @param view
     * @param movie -- clicked movie
     */
    public void onMovieClicked(View view, Movie movie) {
        FragmentManager fm = getSupportFragmentManager();
        Constants.SELECTED_MOVIE = movie;
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, new MovieFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Redirect user to change password screen.
     */
    public void changePassword() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, new ChangePassword());
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Show profile.
     */
    public void loadProfile() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, new ProfileFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * On search button clicked -- change screen to list findings.
     * @param view
     */
    public void onSearchButtonClicked(View view) {
        FragmentManager fm = getSupportFragmentManager();
        Constants.SELECTED_OPTION = Constants.SEARCH;
        EditText toolbar = findViewById(R.id.toolbar);
        Constants.SEARCH_REQUEST = toolbar.getText().toString();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, new HomeFragment());
        ft.addToBackStack(null);
        ft.commit();
    }
}
