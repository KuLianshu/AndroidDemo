package com.example.databindingdemo.model;

import androidx.databinding.ObservableField;

public class Image {

    private ObservableField<String> path;

    private ObservableField<String> url;

    public Image() {
    }

    public Image(String url,String path) {
        this.path = new ObservableField<>(path);
        this.url = new ObservableField<>(url);
    }

    public ObservableField<String> getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public ObservableField<String> getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }
}
