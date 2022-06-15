package com.example.ligtasystem;

public class Announcement {

    String title, date, about;

    public Announcement(String title, String date, String about) {
        this.title = title;
        this.date = date;
        this.about = about;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Announcement(){

    }
}
