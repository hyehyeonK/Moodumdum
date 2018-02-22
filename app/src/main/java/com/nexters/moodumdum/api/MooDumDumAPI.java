package com.nexters.moodumdum.api;

import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.PostContentsModel;
import com.nexters.moodumdum.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public interface MooDumDumAPI {
    //글쓰기
    @POST("api/board")
    Call<ServerResponse> postContents (@FieldMap PostContentsModel contentsModel);
//    @POST("api/board")
//    Call<ServerResponse> postContents (@Field("category_id") BigInteger category_id,
//                                       @Field("user") String user,
//                                       @Field("name") String name,
//                                       @Field("description") String description);
    //글가져오기

    @GET("api/board")
    Call<ContentsModel> getContents ();

    //내가 쓴 글 가져오기
    @GET("api/board")
    Call<ContentsModel> getMyContents (@Query("userId") long userId);

}
