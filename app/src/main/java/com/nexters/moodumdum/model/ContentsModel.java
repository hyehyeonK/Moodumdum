package com.nexters.moodumdum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public class ContentsModel implements Serializable {
    @SerializedName("count") private int count;
    @SerializedName("next") private String next;
    @SerializedName("previous") private String previous;
    @SerializedName("results") private ArrayList<Result> result;

    public  class  Result {
        @SerializedName("id")
        private BigInteger id;
        @SerializedName("category_id")
        private BigInteger category_id;
        @SerializedName("user_id")
        private UserDataModel user;
        @SerializedName("description")
        private String description;
        @SerializedName("comment_count")
        private int comment_count;
        @SerializedName("like_count")
        private int like_count;
        @SerializedName("is_liked")
        private boolean is_liked;
        @SerializedName("views")
        private int views;
        @SerializedName("image_url")
        private String image_url;
        @SerializedName("background_color")
        private String background_color;
        @SerializedName("color")
        private String color;
        @SerializedName("created")
        private String created;
        @SerializedName("updated")
        private String updated;

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public BigInteger getId() {
            return id;
        }

        public BigInteger getCategory_id() {
            return category_id;
        }

        public UserDataModel getUser() {
            return user;}

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


        public String getDescription() {
            return description;
        }

        public int getComment_count() {
            return comment_count;
        }

        public int getLike_count() {
            return like_count;
        }

        public boolean isIs_liked() {
            return is_liked;
        }

        public int getViews() {
            return views;
        }

        public String getImage_url() {
            return image_url;
        }

        public String getBackground_color() {
            return background_color;
        }

        public String getColor() {
            return color;
        }

        public String getCreated() {
            return created;
        }

        public String getUpdated() {
            return updated;
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
