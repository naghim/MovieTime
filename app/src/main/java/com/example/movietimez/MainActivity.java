package com.example.movietimez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, new LoginFragment());
        ft.commit();
    }


    public void afterLogin() {
        Toast.makeText(this,"yaaaaaaaaaaaaas",Toast.LENGTH_LONG);
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, new MovieRecyclerView());
        ft.commit();
    }

    // TODO: Change fragment to movie description page.
    public void onVoteClick(String vote)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //ft.replace(R.id.fragment_place, new MovieDescription());
        ft.addToBackStack(null);
        ft.commit();
    }

}
