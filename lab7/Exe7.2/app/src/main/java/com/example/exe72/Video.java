package com.example.exe72;

public class Video {
    private  String path,thumb;

    public Video(String path, String thumb) {
        this.path = path;
        this.thumb = thumb;
    }

    public Video() {
        
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
