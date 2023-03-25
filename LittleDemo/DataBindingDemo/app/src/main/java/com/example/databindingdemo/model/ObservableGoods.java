package com.example.databindingdemo.model;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableFloat;

public class ObservableGoods {

    private ObservableField<String> name;

    private ObservableFloat price;

    private ObservableField<String> details;

    public ObservableGoods() {
    }

    public ObservableGoods(String name, float price, String details) {
        this.name = new ObservableField<String>(name);
        this.price = new ObservableFloat(price);
        this.details = new ObservableField<String>(details);
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableFloat getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price.set(price);
    }

    public ObservableField<String> getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details.set(details);
    }
}
