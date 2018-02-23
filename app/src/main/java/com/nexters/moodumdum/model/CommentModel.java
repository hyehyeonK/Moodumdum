package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 2018-02-22.
 */

public class CommentModel implements Serializable {
    @SerializedName("count") private int count;
    @SerializedName("next") private String next;
    @SerializedName("previous") private int previous;
    @SerializedName("results") private ArrayList<CommentModel.Result> result;

    public class Result {
        @SerializedName( "id" ) private BigInteger id;
        @SerializedName( "board_id" ) private BigInteger board_id;
        @SerializedName( "user" ) private String user;
        @SerializedName( "name" ) private String name;
        @SerializedName( "description" ) private String description;
        @SerializedName( "created" ) private Date created;
        @SerializedName( "updated" ) private Date updated;

        public BigInteger getId() {
            return id;
        }

        public BigInteger getBoard_id() {
            return board_id;
        }

        public String getUser() {
            return user;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Date getCreated() {
            return created;
        }

        public Date getUpdated() {
            return updated;
        }


        @Override
        public String toString() {
            return "\"Result\" : {" +
                    "\"id\" :" + id +
                    ", \"board_id\" : " + board_id +
                    ", \"user\" : '" + user + '\'' +
                    ", \"name\" : '" + name + '\'' +
                    ", \"description\" : '" + description + '\'' +
                    ", \"created\" : '" + created + '\'' +
                    ", \"updated\" : '" + updated + '\'' +
                    '}';

        }
    }

//    public ArrayList<CommentModel.Result> getResult() {
//        return result;
//    }

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public int getPrevious() {
        return previous;
    }

    public ArrayList<CommentModel.Result> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "count=" + count +
                ", next=" + next +
                ", previous=" + previous +
                ", result=" + result +
                '}';
    }

}
