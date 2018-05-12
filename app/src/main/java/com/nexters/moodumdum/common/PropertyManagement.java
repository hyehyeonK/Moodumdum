package com.nexters.moodumdum.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kimhyehyeon on 2018. 5. 12..
 */

public class PropertyManagement {
    private static final String KEY_ID = "MOODUMDUM_FILE_KEY";
    private static final String DATA_USER_ID = "USER_ID";
//    private static final String DATA_USER_PROFILE = "USER_PROFILE";
    private static final String DATA_USER_NICKNAME = "USER_NICKNAME";
//    private static final String DATA_COMMENT_LIKE_COUNT = "LIKE_COUNT";
    private static final String DATA_COMMENT_LIKE_COUNT = "LIKE_COUNT";

    public static void putUserId(Context context, String userId) {
        SharedPreferences preferences = context.getSharedPreferences(KEY_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DATA_USER_ID, userId);
        editor.apply();
    }

    public static String getUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(KEY_ID, Context.MODE_PRIVATE);
        return preferences.getString(DATA_USER_ID, null);
    }

    public static void putUserProfile(Context context, String user_nickName) {
        SharedPreferences preferences = context.getSharedPreferences(KEY_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DATA_USER_NICKNAME, user_nickName);
//        editor.putString(DATA_USER_PROFILE, user_nickProfile_url);
        editor.apply();
    }

    public static String getUserProfile(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(KEY_ID, Context.MODE_PRIVATE);
        return preferences.getString(DATA_USER_NICKNAME, null);
    }

    public static String getCommentLikeCount(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(KEY_ID, Context.MODE_PRIVATE);
        return preferences.getString(DATA_COMMENT_LIKE_COUNT, null);
    }

//    public static String putCommentLikeCount(Context context, String like_count) {
//        SharedPreferences preferences = context.getSharedPreferences(KEY_ID, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(DATA_COMMENT_LIKE_COUNT, like_count);
//        editor.apply();
//        return preferences.getString(DATA_COMMENT_LIKE_COUNT, null);
//    }


// 로그아웃
//    public static void clearUser(Context context) {
//        SharedPreferences preferences = context.getSharedPreferences(KEY_ID, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear();
//        editor.apply();
//    }
}
