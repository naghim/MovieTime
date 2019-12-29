package com.example.movietimez;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RetroMovie {

//    @SerializedName("description")
//    private String description;
//    @SerializedName("id")
//    private Integer id;
//    @SerializedName("title")
//    private String title;
//    @SerializedName("url")
//    private String url;
//    @SerializedName("thumbnailUrl")
//    private String thumbnailUrl;
//
//    public RetroMovie(String description, Integer id, String title, String url, String thumbnailUrl) {
//        this.description = description;
//        this.id = id;
//        this.title = title;
//        this.url = url;
//        this.thumbnailUrl = thumbnailUrl;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getThumbnailUrl() {
//        return thumbnailUrl;
//    }
//
//    public void setThumbnailUrl(String thumbnailUrl) {
//        this.thumbnailUrl = thumbnailUrl;
//    }

    private int page;
    private int total_pages;
    private int total_results;
    private List<Model> results;

    public RetroMovie(int page, int total_pages, int total_results, List<Model> results) {
        this.page = page;
        this.total_pages = total_pages;
        this.total_results = total_results;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<Model> getResults() {
        return results;
    }

    public void setResults(List<Model> results) {
        this.results = results;
    }
}