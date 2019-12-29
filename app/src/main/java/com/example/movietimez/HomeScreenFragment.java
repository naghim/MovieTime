package com.example.movietimez;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;
import com.example.movietimez.Model;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.base.Supplier;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;


public class HomeScreenFragment extends Fragment {

    private MovieRecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MovieAdapter adapter = null;


    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.homescreen_topten, parent, false);

//        HttpApiService service = RetrofitClientInstance.getRetrofitInstance().create(HttpApiService.class);
//
//        Call<RetroMovie> call = service.getPopularMovies(Constants.API_KEY);
//        call.enqueue(new Callback<RetroMovie>() {
//            @Override
//            public void onResponse(Call<RetroMovie> call, Response<RetroMovie> response) {
//                List<Model> movies = response.body().getResults();
//
//                Toast.makeText(getContext(),"onfailure"+movies.size(),Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<RetroMovie> call, Throwable t) {
//
//            }
//        });




//        RetrofitClientInstance.getRetrofitInstance().create(HttpApiService.class).getTopRatedMovies(  "6aab6249-1829-4991-b437-6f7b990ff6ee", "4", "news", "").enqueue(valami);

       return view;
    }


    //TODO: List top 10 moovie from the moovieDB with retrofit
    // pagination?

    private final Callback<Model> valami = (Callback<Model>)(new Callback<Model>(){
        public void onFailure(@NonNull Call<Model> call, @NonNull Throwable t){
            Toast.makeText(getContext(),"onfailure",Toast.LENGTH_LONG);
        }

        public void onResponse(@NonNull Call<Model> call, @NonNull Response<Model> response){
            Toast.makeText(getContext(),"onresponse",Toast.LENGTH_LONG);
            Model m = (Model)response.body();
            Log.d("ASDDS", m.toString() );
        }
    }) ;

}
