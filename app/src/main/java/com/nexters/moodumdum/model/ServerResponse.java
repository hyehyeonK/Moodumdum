package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kimhyehyeon on 2018. 2. 22..
 */

public class ServerResponse implements Serializable {
    @SerializedName("message")
    String message;
    @SerializedName("status")
    String status;
}
