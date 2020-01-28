package com.example.meeteatsave;

public class Ads {
    String adId;
    String adTitle;

    public Ads(){

    }

    public Ads(String adId, String adTitle) {
        this.adId = adId;
        this.adTitle = adTitle;
    }

    public String getAdId() {
        return adId;
    }

    public String getAdTitle() {
        return adTitle;
    }
}
