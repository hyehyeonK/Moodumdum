package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public class ContentsModel implements Serializable {
    @SerializedName("count") private int count;
    @SerializedName("next") private int next;
    @SerializedName("previous") private int previous;
    @SerializedName("results") private ArrayList<Result> result;
//    @SerializedName(category_id)
//            String category_id;
    public  class  Result {
    @SerializedName("id") private BigInteger id;
    @SerializedName("category_id") private BigInteger category_id;
    @SerializedName("user") private String user;
    @SerializedName("name") private String name;
    @SerializedName("description") private String description;
    @SerializedName("comment_count") private int comment_count;
    @SerializedName("like_count") private int  like_count;
    @SerializedName("is_liked") private boolean is_liked;
    @SerializedName("views") private int views;
    @SerializedName("image_url") private String image_url;
    @SerializedName("background_color") private String background_color;
    @SerializedName("color") private String color;
    @SerializedName("created") private String  created;
    @SerializedName("updated") private String  updated;

    public BigInteger getId() {
        return id;
    }

    public BigInteger getCategory_id() {
        return category_id;
    }

    public String getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getComment_count() {
        return comment_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public boolean isIs_liked() {
        return is_liked;
    }

    public int getViews() {
        return views;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getBackground_color() {
        return background_color;
    }

    public String getColor() {
        return color;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }
}

    public int getCount() {
        return count;
    }

    public int getNext() {
        return next;
    }

    public int getPrevious() {
        return previous;
    }

    public ArrayList<Result> getResult() {
        return result;
    }
}
