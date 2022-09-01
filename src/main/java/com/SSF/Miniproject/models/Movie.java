package com.SSF.Miniproject.models;

import java.io.StringReader;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Movie {

    // popular movie
    // public boolean adult;
    // public String backdrop_path;
    // public List<Integer> genre_ids; // or int 
    public String poster_path;
    // public String title;
    // public String video;
    public int id;
    public String original_language;
    public String original_title;
    public String overview;
    public int popularity;
    public String release_date;
    public int vote_average;
    public int vote_count;

    public int getId() { return id; }
    public void setId(int string) { this.id = string; }

    public String getOriginal_language() { return original_language; }
    public void setOriginal_language(String original_language) {
        this.original_language = original_language; }

    public String getOriginal_title() { return original_title; }
    public void setOriginal_title(String original_title) {
        this.original_title = original_title; }

    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }

    public int getPopularity() { return popularity; }
    public void setPopularity(int popularity) { this.popularity = popularity; }

    public String getRelease_date() { return release_date; }
    public void setRelease_date(String release_date) { this.release_date = release_date; }
    
    public int getVote_average() { return vote_average; }
    public void setVote_average(int vote_average) { this.vote_average = vote_average; }
    
    public int getVote_count() { return vote_count; }
    public void setVote_count(int vote_count) { this.vote_count = vote_count; }

    public String getPoster_path() { return poster_path; }
    public void setPoster_path(String poster_path) { this.poster_path = poster_path; }


    public static Movie createNew(String jsonStr) {
        StringReader strReader = new StringReader(jsonStr);
        JsonReader reader = Json.createReader(strReader);
        return createNew(reader.readObject()); 
    }

    public static Movie createNew(JsonObject jo) {
        Movie m = new Movie();
        // m.setAdult(jo.getBoolean("adult"));
        // m.setBackdrop_path(jo.getString("backdrop_path"));
        // m.setGenre_ids(jo.getBoolean(""));
        m.setId(jo.getInt("id"));
        m.setOriginal_language(jo.getString("original_language"));
        m.setOriginal_title(jo.getString("original_title"));
        m.setOverview(jo.getString("overview"));
        m.setPopularity(jo.getInt("popularity"));
        m.setRelease_date(jo.getString("release_date"));
        m.setVote_average(jo.getInt("vote_average"));
        m.setVote_count(jo.getInt("vote_count"));
        m.setPoster_path(jo.getString("poster_path"));
        return m;
    }
    
    public static Movie create(JsonObject jo) {
        Movie m = new Movie();
        // m.setAdult(jo.getBoolean("adult"));
        // m.setBackdrop_path(jo.getString("backdrop_path"));
        // m.setGenre_ids(jo.getBoolean(""));
        m.setId(jo.getInt("id"));
        m.setOriginal_language(jo.getString("original_language"));
        m.setOriginal_title(jo.getString("original_title"));
        m.setOverview(jo.getString("overview"));
        m.setPopularity(jo.getInt("popularity"));
        m.setPoster_path(jo.getString("poster_path"));
        m.setRelease_date(jo.getString("release_date"));
        // m.setTitle(jo.getString("title"));
        // m.setVideo(jo.getString("video"));
        m.setVote_average(jo.getInt("vote_average"));
        m.setVote_count(jo.getInt("vote_count"));
        return m;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("id", id)
            .add("original_language", original_language)
            .add("original_title", original_title)
            .add("overview", overview)
            .add("popularity", popularity)
            .add("poster_path", poster_path)
            .add("release_date", release_date)
            // .add("title", title)
            // .add("video", video)
            .add("vote_average", vote_average)
            .add("vote_count", vote_count)
            .build();
    }
}