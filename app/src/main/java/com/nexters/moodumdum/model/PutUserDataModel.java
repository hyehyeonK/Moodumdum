package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 2018-04-08.
 */

public class PutUserDataModel {
    @SerializedName( "user" ) public String user;
    @SerializedName( "name" ) public String name;
    @SerializedName( "profile_image" ) public String profile_image;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public PutUserDataModel(String uuid, String nickName) {
        this.user = user;
        this.name = name;
        this.profile_image = profile_image;
    }


}
