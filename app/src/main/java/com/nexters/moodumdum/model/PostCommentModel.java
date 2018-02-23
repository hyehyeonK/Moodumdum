package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by User on 2018-02-22.
 */

public class PostCommentModel implements Serializable {
    @SerializedName( "board_id" ) private BigInteger board_id;
    @SerializedName( "user" ) private String user;
    @SerializedName( "name" ) private String name;
    @SerializedName( "description" ) private String description;

    public BigInteger getBoard_id() {
        return board_id;
    }

    public void setBoard_id(BigInteger board_id) {
        this.board_id = board_id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PostCommentModel{" +
                "board_id=" + board_id +
                ", user='" + user + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
