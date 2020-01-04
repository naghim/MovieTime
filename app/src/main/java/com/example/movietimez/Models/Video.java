package com.example.movietimez.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Video {

    @SerializedName("id")
    public String id;
    @SerializedName("key")
    public String key;
    @SerializedName("site")
    public String site;
    @SerializedName("type")
    public String type;

    public Video(String id, String key, String site, String type) {
        this.id = id;
        this.key = key;
        this.site = site;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
