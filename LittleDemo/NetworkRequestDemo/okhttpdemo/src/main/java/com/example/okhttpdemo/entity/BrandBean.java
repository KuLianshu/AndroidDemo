package com.example.okhttpdemo.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("DOCUMENT")//这里用到注解(必须写)
public class BrandBean {

    @XStreamImplicit(itemFieldName="item")//节点注解(必须写)
    List<Item> item;

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public static class Item {
        String device_type_id;
        String brand_id;
        String remote_id;
        String rank;

        public String getDevice_type_id() {
            return device_type_id;
        }

        public void setDevice_type_id(String device_type_id) {
            this.device_type_id = device_type_id;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getRemote_id() {
            return remote_id;
        }

        public void setRemote_id(String remote_id) {
            this.remote_id = remote_id;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "device_type_id='" + device_type_id + '\'' +
                    ", brand_id='" + brand_id + '\'' +
                    ", remote_id='" + remote_id + '\'' +
                    ", rank='" + rank + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BrandBean{" +
                "item=" + item.toString() +
                '}';
    }
}