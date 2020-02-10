package com.example.meeteatsave;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Ads implements Serializable {

    public String uid;
    public String title;
    public String foodArt;
    public String city;
    public String price;
    public String numberOfSeats;
    public String date;
    public String time;
    public int starCount = 0;

    public Ads() {
        // Default constructor required for calls to DataSnapshot.getValue(Ads.class)
    }

    public Ads(String uid, String title, String foodArt, String city, String  price, String numberOfSeats, String date, String time) {
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFoodArt() {
        return foodArt;
    }

    public void setFoodArt(String foodArt) {
        this.foodArt = foodArt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(String numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
