package com.example.movietimez;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movietimez.Models.Model;

import java.util.List;

public class Constants {

    public static final String API_KEY = "dbe18d406caefac22099e49713272420";
    public static final String YT_API_KEY = "AIzaSyAeiWDnxYqpw2v6BhTNKYTMl2oPxeaB-yk";
    public static final String BASE_URI = "https://api.themoviedb.org/3/";
    public static final int PICK_IMAGE = 1;
    public static List<Model> RELATED_CONTENT = null;
    public static int PAGE = 1;

    public static Model SELECTED_MOVIE = null;
    public static String SELECTED_OPTION = "HOME";
    public static String SEARCH = "SEARCH";
    public static String FAVS = "FAVS";
    public static String HOME = "HOME";
    public static String USERNAME = "TEST";
    public static String SEARCH_REQUEST = "DRAGONS";

}
