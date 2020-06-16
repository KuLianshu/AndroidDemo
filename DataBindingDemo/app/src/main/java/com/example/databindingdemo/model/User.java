package com.example.databindingdemo.model;

import androidx.databinding.BaseObservable;

public class User extends BaseObservable {

    private String id;

    private String name;

    private String password;

    private String blog;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String id, String name, String password, String blog) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.blog = blog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }
}
