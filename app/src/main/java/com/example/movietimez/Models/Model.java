package com.example.movietimez.Models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {

        @SerializedName("adult")
        public boolean adult;
        @SerializedName("backdrop_path")
        public String backdropPath;
        @SerializedName("budget")
        public int budget;
        @SerializedName("gender_ids")
        public List<Object> gender_ids = null;
        @SerializedName("homepage")
        public String homepage;
        @SerializedName("id")
        public int id;
        @SerializedName("imdb_id")
        public String imdbId;
        @SerializedName("original_language")
        public String originalLanguage;
        @SerializedName("original_title")
        public String originalTitle;
        @SerializedName("overview")
        public String overview;
        @SerializedName("popularity")
        public float popularity;
        @SerializedName("poster_path")
        public String posterPath;
        @SerializedName("release_date")
        public String releaseDate;
        @SerializedName("revenue")
        public int revenue;
        @SerializedName("runtime")
        public int runtime;
        @SerializedName("status")
        public String status;
        @SerializedName("tagline")
        public String tagline;
        @SerializedName("title")
        public String title;
        @SerializedName("video")
        public boolean video;
        @SerializedName("vote_average")
        public float voteAverage;
        @SerializedName("vote_count")
        public int voteCount;

        public Model(boolean adult, String backdropPath, int budget, List<Object> gender_ids, String homepage, int id, String imdbId, String originalLanguage, String originalTitle, String overview, float popularity, String posterPath, String releaseDate, int revenue, int runtime, String status, String tagline, String title, boolean video, float voteAverage, int voteCount) {
                this.adult = adult;
                this.backdropPath = backdropPath;
                this.budget = budget;
                this.gender_ids = gender_ids;
                this.homepage = homepage;
                this.id = id;
                this.imdbId = imdbId;
                this.originalLanguage = originalLanguage;
                this.originalTitle = originalTitle;
                this.overview = overview;
                this.popularity = popularity;
                this.posterPath = posterPath;
                this.releaseDate = releaseDate;
                this.revenue = revenue;
                this.runtime = runtime;
                this.status = status;
                this.tagline = tagline;
                this.title = title;
                this.video = video;
                this.voteAverage = voteAverage;
                this.voteCount = voteCount;
        }


    public boolean isAdult() {
                return adult;
        }

        public void setAdult(boolean adult) {
                this.adult = adult;
        }

        public String getBackdropPath() {
                return backdropPath;
        }

        public void setBackdropPath(String backdropPath) {
                this.backdropPath = backdropPath;
        }

        public int getBudget() {
                return budget;
        }

        public void setBudget(int budget) {
                this.budget = budget;
        }

        public List<Object> getGender_ids() {
                return gender_ids;
        }

        public void setGender_ids(List<Object> gender_ids) {
                this.gender_ids = gender_ids;
        }

        public String getHomepage() {
                return homepage;
        }

        public void setHomepage(String homepage) {
                this.homepage = homepage;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getImdbId() {
                return imdbId;
        }

        public void setImdbId(String imdbId) {
                this.imdbId = imdbId;
        }

        public String getOriginalLanguage() {
                return originalLanguage;
        }

        public void setOriginalLanguage(String originalLanguage) {
                this.originalLanguage = originalLanguage;
        }

        public String getOriginalTitle() {
                return originalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
                this.originalTitle = originalTitle;
        }

        public String getOverview() {
                return overview;
        }

        public void setOverview(String overview) {
                this.overview = overview;
        }

        public float getPopularity() {
                return popularity;
        }

        public void setPopularity(float popularity) {
                this.popularity = popularity;
        }

        public String getPosterPath() {
                return "https://image.tmdb.org/t/p/w500"+posterPath;
        }

        public void setPosterPath(String posterPath) {
                this.posterPath = posterPath;
        }

        public String getReleaseDate() {
                return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
                this.releaseDate = releaseDate;
        }

        public int getRevenue() {
                return revenue;
        }

        public void setRevenue(int revenue) {
                this.revenue = revenue;
        }

        public int getRuntime() {
                return runtime;
        }

        public void setRuntime(int runtime) {
                this.runtime = runtime;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public String getTagline() {
                return tagline;
        }

        public void setTagline(String tagline) {
                this.tagline = tagline;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public boolean isVideo() {
                return video;
        }

        public void setVideo(boolean video) {
                this.video = video;
        }

        public float getVoteAverage() {
                return voteAverage;
        }

        public void setVoteAverage(float voteAverage) {
                this.voteAverage = voteAverage;
        }

        public int getVoteCount() {
                return voteCount;
        }

        public void setVoteCount(int voteCount) {
                this.voteCount = voteCount;
        }


}
