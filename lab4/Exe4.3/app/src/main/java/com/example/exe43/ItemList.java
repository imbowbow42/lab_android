package com.example.exe43;

import java.io.Serializable;

public class ItemList implements Serializable {


    private int imgResource;
    private String titulo;
    private boolean checkBox;


    public ItemList(int imgResource, String titulo, boolean checkBox) {

        this.imgResource = imgResource;
        this.titulo = titulo;
        this.checkBox = checkBox;
    }

    public ItemList( int imgResource, String titulo) {

        this.imgResource = imgResource;
        this.titulo = titulo;
        this.checkBox = false;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }
    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

}
