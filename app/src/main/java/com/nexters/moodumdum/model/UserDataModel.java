package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kimhyehyeon on 2018. 3. 28..
 */

public class UserDataModel implements Serializable {
    @SerializedName( "user" ) private String uuid;
    @SerializedName( "name" ) private String nickName;
    @SerializedName( "profile_image" ) private String profile_image;
    @SerializedName( "like_count" ) private int like_count;
    @SerializedName( "board_count" ) private int board_count;

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

    public int getLike_count() {
        return like_count;
    }

    public int getBoard_count() {
        return board_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public void setBoard_count(int board_count) {
        this.board_count = board_count;
    }
}
