package com.example.exe102;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Window;

import androidx.annotation.ColorInt;

public class ThemeColor {

    Resources.Theme theme;
    Window window;

    @ColorInt int colorPrimary;
    @ColorInt int colorOnPrimary;
    @ColorInt int colorPrimaryContainer;
    @ColorInt int colorOnPrimaryContainer;
    @ColorInt int colorSecondary;
    @ColorInt int colorOnSecondary;
    @ColorInt int colorSecondaryContainer;
    @ColorInt int colorOnSecondaryContainer;
    @ColorInt int colorTertiaryContainer;
    @ColorInt int colorOnTertiaryContainer;
    @ColorInt int colorBackground;
    @ColorInt int colorOnBackground;
    @ColorInt int colorError;
    @ColorInt int colorOnError;
    @ColorInt int colorSurface;
    @ColorInt int colorOnSurface;
    @ColorInt int colorSurfaceVariant;
    @ColorInt int colorOnSurfaceVariant;

    public ThemeColor(Resources.Theme theme, Window window) {
        this.theme = theme;
        this.window = window;
    }

    @ColorInt
    int colorAttrResolve(int res) {
        TypedValue colorValue = new TypedValue();
        theme.resolveAttribute(res, colorValue, true);
        return colorValue.data;
    }
    public Resources.Theme getTheme() {
        return theme;
    }

    public int getColorPrimary() {
        return  colorAttrResolve(com.google.android.material.R.attr.colorPrimary);
    }

    public int getColorOnPrimary() {
        return colorAttrResolve(com.google.android.material.R.attr.colorOnPrimary);
    }

    public int getColorPrimaryContainer() {
        return colorAttrResolve(com.google.android.material.R.attr.colorPrimaryContainer);
    }

    public int getColorOnPrimaryContainer() {
        return colorAttrResolve(com.google.android.material.R.attr.colorOnPrimaryContainer);
    }

    public int getColorSecondary() {
        return colorAttrResolve(com.google.android.material.R.attr.colorSecondary);
    }

    public int getColorOnSecondary() {
        return colorAttrResolve(com.google.android.material.R.attr.colorOnSecondary);
    }

    public int getColorSecondaryContainer() {
        return colorAttrResolve(com.google.android.material.R.attr.colorSecondaryContainer);
    }

    public int getColorOnSecondaryContainer() {
        return colorAttrResolve(com.google.android.material.R.attr.colorOnSecondaryContainer);
    }

    public int getColorTertiaryContainer() {
        return colorAttrResolve(com.google.android.material.R.attr.colorTertiaryContainer);
    }

    public int getColorOnTertiaryContainer() {
        return colorAttrResolve(com.google.android.material.R.attr.colorOnTertiaryContainer);
    }

    public int getColorBackground() {
        return window.getStatusBarColor();
    }

    public int getColorOnBackground() {
        return colorAttrResolve(com.google.android.material.R.attr.colorOnBackground);
    }

    public int getColorError() {
        return colorAttrResolve(com.google.android.material.R.attr.colorError);
    }

    public int getColorOnError() {
        return colorAttrResolve(com.google.android.material.R.attr.colorOnError);
    }

    public int getColorSurface() {
        return colorAttrResolve(com.google.android.material.R.attr.colorSurface);
    }

    public int getColorOnSurface() {
        return colorAttrResolve(com.google.android.material.R.attr.colorOnSurface);
    }

    public int getColorSurfaceVariant() {
        return colorAttrResolve(com.google.android.material.R.attr.colorSurfaceVariant);
    }

    public int getColorOnSurfaceVariant() {
        return colorAttrResolve(com.google.android.material.R.attr.colorOnSurfaceVariant);
    }
}
