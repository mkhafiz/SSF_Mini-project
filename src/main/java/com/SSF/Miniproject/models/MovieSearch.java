package com.SSF.Miniproject.models;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class MovieSearch {

   public String poster_path;
   public String overview;
   public String release_date;
   public int id;
   public String original_title;
   public String original_language;
   public String backdrop_path;
   public int total_results;
   public int total_pages;

public String getPoster_path() {    return poster_path;}
public void setPoster_path(String poster_path) {    this.poster_path = poster_path;}

public String getOverview() {    return overview;}
public void setOverview(String overview) {    this.overview = overview;}

public String getRelease_date() {    return release_date;}
public void setRelease_date(String release_date) {    this.release_date = release_date;}

public int getId() {    return id;}
public void setId(int id) {    this.id = id;}

public String getOriginal_title() {    return original_title;}
public void setOriginal_title(String original_title) {    this.original_title = original_title;}

public String getOriginal_language() {    return original_language;}
public void setOriginal_language(String original_language) {    this.original_language = original_language;}

public String getBackdrop_path() {    return backdrop_path;}
public void setBackdrop_path(String backdrop_path) {    this.backdrop_path = backdrop_path;}

public int getTotal_results() {    return total_results;}
public void setTotal_results(int total_results) {    this.total_results = total_results;}

public int getTotal_pages() {    return total_pages;}
public void setTotal_pages(int total_pages) {    this.total_pages = total_pages;}


public static MovieSearch createNew(String jsonStr) {
    StringReader strReader = new StringReader(jsonStr);
    JsonReader reader = Json.createReader(strReader);
    return createNew(reader.readObject()); 
}

public static MovieSearch createNew(JsonObject jo) {
    MovieSearch s = new MovieSearch();
        s.setId(jo.getInt("id"));
        s.setOriginal_title(jo.getString("original_title"));
        s.setOverview(jo.getString("overview"));
        s.setRelease_date(jo.getString("release_date"));
        s.setTotal_pages(jo.getInt("total_results"));
        s.setTotal_results(jo.getInt("total_pages"));
        s.setBackdrop_path(jo.getString("backdrop_path"));
        return s;
    }
    
    public static MovieSearch create(JsonObject jo) {
        MovieSearch s = new MovieSearch();
        s.setId(jo.getInt("id"));
        s.setOriginal_title(jo.getString("original_title"));
        s.setOverview(jo.getString("overview"));
        s.setRelease_date(jo.getString("release_date"));
        s.setTotal_pages(jo.getInt("total_results"));
        s.setTotal_results(jo.getInt("total_pages"));
        s.setBackdrop_path(jo.getString("backdrop_path"));
        return s;
    }
    // whats the diff + which one to use

    public static MovieSearch specMovie(
        int id, String backdrop_path, String original_title, String overview, 
        String release_date
        ) {
            MovieSearch s = new MovieSearch();

            s.setId(id);
            s.setBackdrop_path(backdrop_path);
            s.setOriginal_title(original_title);
            s.setOverview(overview);
            s.setRelease_date(release_date);
            // s.setTotal_pages(total_results);
            // s.setTotal_results(total_pages);

            // cos OUTSIDE "RESULT"

            return s;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("id", id)
            .add("original_title", original_title)
            .add("overview", overview)
            .add("release_date", release_date)
            .add("total_results", total_results)
            .add("total_pages", total_pages)
            .add("backdrop_path", backdrop_path)
            .build();
    }
}