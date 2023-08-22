package com.example.exe63;

public class Event {
    private int id;
    private String name;
    private String place;
    private String date;
    private String time;
    private Boolean completed;

    public Event(int id, String name, String place, String date, String time, Boolean completed) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.date = date;
        this.time = time;
        this.completed = completed;
    }
    public Event(String name, String place, String date, String time) {
        this.name = name;
        this.place = place;
        this.date = date;
        this.time = time;

        setCompleted(false);
    }

    public Event() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
