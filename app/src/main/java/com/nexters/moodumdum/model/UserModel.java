package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public class UserModel implements Serializable{
    @SerializedName( "like_count" ) private int like_count;
    @SerializedName( "board_count" ) private int board_count;

    public int getLike_count() {
        return like_count;
    }

    public int getBoard_count() {
        return board_count;
    }
}
