package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kimhyehyeon on 2018. 2. 24..
 */

public class ImageModel implements Serializable {
    @SerializedName("count") private int count;
    @SerializedName("next") private String next;
    @SerializedName("previous") private String previous;
    @SerializedName("results") private ArrayList<Result> result;

    public  class  Result {
        @SerializedName("pk") private int pk;
        @SerializedName("image_url") private  String image_url;
        @SerializedName("font_color") private String font_color;

        public int getPk() {
            return pk;
        }

        public String getImage_url() {
            return image_url;
        }

        public String getFont_color() {
            return font_color;
        }
    }

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public ArrayList<Result> getResult() {
        return result;
    }
}
