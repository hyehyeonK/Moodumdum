package com.nexters.moodumdum.model;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by kimhyehyeon on 2018. 2. 22..
 */

public class PostContentsModel implements Serializable{
    private BigInteger category_id;
    private String email; //uuid
    private String name; //not null
    private String description; //not null
//    private int views; -뭐지이게
    private String image_url;
    private String background_color;
    private String color;

    public BigInteger getCategory_id() {
        return category_id;
    }

    public void setCategory_id(BigInteger category_id) {
        this.category_id = category_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public int getViews() {
//        return views;
//    }
//
//    public void setViews(int views) {
//        this.views = views;
//    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getBackground_color() {
        return background_color;
    }

    public void setBackground_color(String background_color) {
        this.background_color = background_color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
