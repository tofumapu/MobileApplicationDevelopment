package com.example.a23520231_lab02_bai5;

public class Dishes {
    private String name;
    private int thumbnail;
    private boolean promotion;
    public Dishes() {}
    public Dishes(String name, int thumbnail, boolean promotion) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.promotion = promotion;
    }
    public Dishes(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.promotion = false;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
    public boolean isPromotion() {
        return promotion;
    }
    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }
}
