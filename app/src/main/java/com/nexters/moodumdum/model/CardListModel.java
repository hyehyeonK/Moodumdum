package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public class CardListModel implements Serializable {
    @SerializedName("count") public int count;
    @SerializedName("next") public String next;
    @SerializedName("previous") public String previous;
    @SerializedName("results") public ArrayList<CardDataModel> result;

}