package com.example.ipcdemo.entity;

import android.os.IInterface;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 2172917453284179037L;



    private int id;

    private String msg;

    private boolean state;

    public User() {
    }

    public User(int id, String msg, boolean state) {
        this.id = id;
        this.msg = msg;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", state=" + state +
                '}';
    }
}
