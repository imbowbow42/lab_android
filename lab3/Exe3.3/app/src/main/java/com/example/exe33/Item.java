package com.example.exe33;

public class Item {

    private int imgAvatar;
    private String text;
    private boolean checkBox;



    public Item(int imgAvatar, String text, boolean checkBox) {
        this.imgAvatar = imgAvatar;
        this.text = text;
        this.checkBox = checkBox;


    }

    public Item(int imgAvatar, String text) {
        this.imgAvatar = imgAvatar;
        this.text = text;
        this.checkBox = false;
    }

    public int getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(int imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }


}
