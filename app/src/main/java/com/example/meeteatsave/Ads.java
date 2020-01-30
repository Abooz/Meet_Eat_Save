package com.example.meeteatsave;

import com.google.firebase.database.Exclude;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Ads {

    public String uid;
    public String title;
    public String foodArt;
    public String city;
    public double price;
    public int numberOfSeats;
    public Date date;
    public Time time;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Ads() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Ads(String uid, String title, String foodArt, String city, double price, int numberOfSeats, Date date, Time time) {
        this.uid = uid;
        this.title = title;
        this.foodArt = foodArt;
        this.city =city;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
        this.date = date;
        this.time = time;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("title", title);
        result.put("foodArt", foodArt);
        result.put("starCount", starCount);
        result.put("city", city);
        result.put("price", price);
        result.put("numberOfSeats", numberOfSeats);
        result.put("date", date);
        result.put("time", time);

        return result;
    }
}
