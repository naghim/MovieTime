package com.example.movietimez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;
    BottomNavigationView bottomNav;

    @Override
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

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_favourites:
                        selectedFragment = new FavouritesFragment();
                        break;
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_nowplaying:
                        selectedFragment = new CinemaFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place,
                        selectedFragment).commit();

                return true;
            };

    public void afterLogin() {
        bottomNav.setVisibility(View.VISIBLE);
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, new HomeFragment());
        ft.commit();
    }

    public void onMovieClicked(View view, Model model) {
        FragmentManager fm = getSupportFragmentManager();
        Constants.SELECTED_MOVIE = model;
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, new MovieFragment());
        ft.addToBackStack(null);
        ft.commit();
    }
}
