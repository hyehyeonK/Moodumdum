package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

public class CommentUserModel implements Serializable {
    @SerializedName("count") private int count;
    @SerializedName("next") private String next;
    @SerializedName("previous") private int previous;
    @SerializedName("results") private ArrayList<CommentUserModel.Result> result;

    public class Result {
        @SerializedName( "id" ) private BigInteger id;
        @SerializedName( "board_id" ) private BigInteger board_id;
        @SerializedName( "user_id" ) private CommentUserModel.Result.UserDataModel user;
        @SerializedName( "description" ) private String description;
        @SerializedName( "like_count" )private int like_count;
        @SerializedName( "is_liked" ) private boolean is_liked;
        @SerializedName( "created" ) private Date created;
        @SerializedName( "updated" ) private Date updated;

        public BigInteger getId() {
            return id;
        }

        public BigInteger getBoard_id() {
            return board_id;
        }

        public CommentUserModel.Result.UserDataModel getUser() {
            return user;
        }


        public String getDescription() {
            return description;
        }

        public int getLike_count() {
            return like_count;
        }

        public boolean isIs_liked() {
            return is_liked;
        }

        public Date getCreated() {
            return created;
        }

        public Date getUpdated() {
            return updated;
        }

        public class UserDataModel {
            @SerializedName("user")
            private String uuid;
            @SerializedName("name")
            private String nickName;
            @SerializedName("profile_image")
            private String profile_image;

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
        }



        @Override
        public String toString() {
            return "\"Result\" : {" +
                    "\"id\" :" + id +
                    ", \"board_id\" : " + board_id +
                    ", \"user\" : '" + user + '\'' +
                    ", \"description\" : '" + description + '\'' +
                    ", \"like_count\" : '" + like_count + '\'' +
                    ", \"is_liked\" : '" + is_liked + '\'' +
                    ", \"created\" : '" + created + '\'' +
                    ", \"updated\" : '" + updated + '\'' +
                    '}';

        }
    }
    public ArrayList<CommentUserModel.Result> getResult() {
        return result;
    }

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public int getPrevious() {
        return previous;
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
