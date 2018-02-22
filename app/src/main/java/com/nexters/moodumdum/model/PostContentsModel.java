package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

import javax.annotation.Nullable;

/**
 * Created by kimhyehyeon on 2018. 2. 22..
 */

public class PostContentsModel implements Serializable{
    @SerializedName("category_id") private BigInteger category_id;
    @SerializedName("user") private String user; //uuid
    @SerializedName("name") private String name; //not null
    @SerializedName("description") private String description; //not null
    @Nullable @SerializedName("views") private int views;
    @Nullable @SerializedName("comment_count") private int comment_count;
    @Nullable @SerializedName("image_url") private String image_url;
    @Nullable @SerializedName("background_color") private String background_color;
    @Nullable @SerializedName("color") private String color;
    @Nullable @SerializedName("like_count") private int like_count;
    @Nullable @SerializedName("is_liked") private boolean is_liked;
    @Nullable @SerializedName("created") private Date created;
    @Nullable @SerializedName("updated") private Date updated;


    public BigInteger getCategory_id() {
        return category_id;
    }

    public void setCategory_id(BigInteger category_id) {
        this.category_id = category_id;
    }

    public String getUuid() {
        return user;
    }



    public void setUuid(String uuid) {
        this.user = uuid;
    }

    public String getNickName() {
        return name;
    }

    public void setNickName(String nickName) {
        this.name = nickName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "PostContentsModel{" +
                "category_id=" + category_id +
                ", user='" + user + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }

    //    @Override
//    public String toString() {
//        return "PostContentsModel{" +
//                "category_id=" + category_id +
//                ", uuid='" + uuid + '\'' +
//                ", nickName='" + nickName + '\'' +
//                ", description='" + description + '\'' +
//                ", views=" + views +
//                ", comment_count=" + comment_count +
//                ", image_url='" + image_url + '\'' +
//                ", background_color='" + background_color + '\'' +
//                ", color='" + color + '\'' +
//                ", like_count=" + like_count +
//                ", is_liked=" + is_liked +
//                ", created=" + created +
//                ", updated=" + updated +
//                '}';
//    }
}
