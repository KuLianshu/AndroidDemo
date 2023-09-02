package com.example.databinding.test;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.example.databinding.BR;
import com.example.databinding.entity.User;

public class UserViewModel  {

    private ObservableField<User> userObservableField;

    public UserViewModel(){
        User user = new User("Jack");
        userObservableField = new ObservableField<>();
        userObservableField.set(user);
    }

    public String getUserName(){
        return userObservableField.get().getUserName();
    }

    public void setUserName(String userName){
        userObservableField.get().setUserName(userName);
    }
}
