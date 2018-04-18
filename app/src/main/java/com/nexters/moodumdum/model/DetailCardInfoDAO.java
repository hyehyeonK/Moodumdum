package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by User on 2018-02-22.
 */

public class DetailCardInfoDAO implements Serializable {
    @SerializedName( "board_id" ) private BigInteger board_id;
    @SerializedName( "description" ) private String description;
    @SerializedName( "color") private String color;
    @SerializedName( "backImagUrl") private String backImagUrl;
    @SerializedName( "likeCount") private int likeCount;
    @SerializedName( "isLike") private boolean isLike;
    @SerializedName( "commentCount") private int commentCount;
//    @SerializedName( "currnetView") private View currnetView;


    public BigInteger getBoard_id() {
        return board_id;
    }

    public void setBoard_id(BigInteger board_id) {
        this.board_id = board_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackImagUrl() {
        return backImagUrl;
    }

    public void setBackImagUrl(String backImagUrl) {
        this.backImagUrl = backImagUrl;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

//    public View getCurrnetView() {
//        return currnetView;
//    }
//
//    public void setCurrnetView(View currnetView) {
//        this.currnetView = currnetView;
//    }
}
