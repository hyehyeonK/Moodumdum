package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kimhyehyeon on 2018. 3. 14..
 */

public class CategoryInfoModel implements Serializable{
    @SerializedName( "title" ) private String title;
    @SerializedName( "banner" ) private String banner;

    public String getTitle() {
        return title;
    }

    public String getBanner() {
        return banner;
    }
}
