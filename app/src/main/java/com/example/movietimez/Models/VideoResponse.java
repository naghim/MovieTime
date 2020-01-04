package com.example.movietimez.Models;

import com.example.movietimez.Models.Model;

import java.util.List;

public class VideoResponse {

    private int id;
    private List<Video> results;

    public VideoResponse(int id, List<Video> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}