package com.example.mylibrary;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    String name;
    int num;

    public Book() {
    }

    public Book(String name, int num) {
        this.name = name;
        this.num = num;
    }

    protected Book(Parcel in) {
        name = in.readString();
        num = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(num);
    }

    public void readFromParcel(Parcel in){
        this.name = in.readString();
        this.num = in.readInt();
    }


}
