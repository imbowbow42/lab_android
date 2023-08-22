package com.example.lab8;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("job")
    public String job;
    @SerializedName("createdAt")
    public String createdAt;

    public User(String name, String job) {
        this.name = name;
        this.job = job;
    }
}
