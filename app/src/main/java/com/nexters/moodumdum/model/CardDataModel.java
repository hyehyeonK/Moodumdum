package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by kimhyehyeon on 2018. 11. 5..
 */

public class CardDataModel implements Serializable {
    @SerializedName("id") public BigInteger id;
    @SerializedName("category_id") public BigInteger category_id;
    @SerializedName("user_id") public UserModel user;
    @SerializedName("description") public String description;
    @SerializedName("comment_count") public int comment_count;
    @SerializedName("like_count") public int like_count;
    @SerializedName("is_liked") public boolean is_liked;
    @SerializedName("views") public int views;
    @SerializedName("image_url") public String image_url;
    @SerializedName("background_color") public String background_color;
    @SerializedName("color") public String color;
}
