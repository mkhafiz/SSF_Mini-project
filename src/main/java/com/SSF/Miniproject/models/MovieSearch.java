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
   public int budget;
   public int revenue;
   public String status;
   public String tagline;
   public String name;
   public String username;

public String getUsername() {    return username;}
public void setUsername(String username) {    this.username = username;}   

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

public int getBudget() {return budget;}
public void setBudget(int budget) {    this.budget = budget;}

public int getRevenue() {    return revenue;}
public void setRevenue(int revenue) {    this.revenue = revenue;}

public String getStatus() {    return status;}
public void setStatus(String status) {    this.status = status;}

public String getTagline() {    return tagline;}
public void setTagline(String tagline) {    this.tagline = tagline;}

public String getName() {    return name;}
public void setName(String name) {    this.name = name;}


public static MovieSearch searchMovie(
    int id,  
    // String poster_path,
    String original_title, String overview,
    String release_date
    // int total_results,
    // int total_pages
    ) {
    MovieSearch m1 = new MovieSearch();

    m1.setId(id);
    // m1.setPoster_path(poster_path);
    m1.setOriginal_title(original_title);
    m1.setOverview(overview);
    m1.setRelease_date(release_date);
    // m1.setBackdrop_path(backdrop_path);
    // m1.setTotal_results(total_results);
    // m1.setTotal_pages(total_pages);
    return m1;
}

 public static MovieSearch specMovie(
    int id, 
    String backdrop_path, 
    String original_title, 
    String overview, 
    String release_date, 
    int budget,
    int revenue,
    String status,
    String tagline,
    String name,
    String poster_path
    ) {
        MovieSearch m2 = new MovieSearch();

        m2.setId(id);
        m2.setBackdrop_path(backdrop_path);
        m2.setOriginal_title(original_title);
        m2.setOverview(overview);
        m2.setRelease_date(release_date);
        m2.setBudget(budget);
        m2.setRevenue(revenue);
        m2.setStatus(status);
        m2.setTagline(tagline);
        m2.setName(name);
        m2.setPoster_path(poster_path);
        return m2;

}

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