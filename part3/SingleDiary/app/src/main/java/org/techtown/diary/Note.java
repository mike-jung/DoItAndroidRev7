package org.techtown.diary;

public class Note {
    int _id;
    String weather;
    String address;
    String locationX;
    String locationY;
    String contents;
    String mood;
    String picture;
    String createDateStr;

    public Note(int _id, String weather, String address, String locationX, String locationY, String contents, String mood, String picture, String createDateStr) {
        this._id = _id;
        this.weather = weather;
        this.address = address;
        this.locationX = locationX;
        this.locationY = locationY;
        this.contents = contents;
        this.mood = mood;
        this.picture = picture;
        this.createDateStr = createDateStr;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }
}
