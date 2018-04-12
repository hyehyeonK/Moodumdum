package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 2018-04-08.
 */

public class PutUserDataModel {
    @SerializedName( "user" ) public String uuid;
    @SerializedName( "name" ) public String nickName;
    @SerializedName( "profile_image" ) public String profile_image;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public PutUserDataModel(String uuid, String nickName) {
        this.uuid = uuid;
        this.nickName = nickName;
        this.profile_image = profile_image;
    }


}
