package com.example.szymonapp005;

public class SliderItem {
    String name;
    String url;
    double creationTime;
    int size;

    @Override
    public String toString() {
        return "SliderItem{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", creationTime=" + creationTime +
                ", size=" + size +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(double creationTime) {
        this.creationTime = creationTime;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SliderItem(String name, String url, double creationTime, int size) {
        this.name = name;
        this.url = url;
        this.creationTime = creationTime;
        this.size = size;
    }
}
