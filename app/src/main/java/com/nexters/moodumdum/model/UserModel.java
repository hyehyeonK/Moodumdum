package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public class UserModel implements Serializable{
    @SerializedName( "user" ) public String user;
    @SerializedName( "name" ) public String name;
    @SerializedName( "profile_image" ) public String profile_image;
}
