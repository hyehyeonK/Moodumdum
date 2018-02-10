package com.nexters.moodumdum.api;

import com.nexters.moodumdum.model.Contents;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public interface MooDumDumAPI {
    @GET("api/board")
    Call<Contents> getContents (@Query("userId") long userId);
}
