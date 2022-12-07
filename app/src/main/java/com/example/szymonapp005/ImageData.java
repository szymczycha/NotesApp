package com.example.szymonapp005;

import java.io.Serializable;

public class ImageData implements Serializable {
    private int x;
    private int y;
    private int width;
    private int height;

    @Override
    public String toString() {
        return "ImageData{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public ImageData(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
