package com.nexters.moodumdum.api;

import com.nexters.moodumdum.model.CommentModel;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.PostCommentModel;
import com.nexters.moodumdum.model.PostContentsModel;
import com.nexters.moodumdum.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
    //글가져오기 --> 성공
    @GET("api/board")
    Call<ContentsModel> getContents ();

    //내가 쓴 글 가져오기
    @GET("api/board")
    Call<ContentsModel> getMyContents (@Query("userId") long userId);

//    //카테고리별 컨텐츠 가져오기 ( 최신순 )
    @GET("api/board/search/category/{category_id}")
    Call<ContentsModel> getCategoryContentsInOrderOfPriority (@Path("category_id") String category_id);

    //getCategoryContentsInOrderOfPopularity 인기순 함수명 할꼬

    //모든 댓글 가져오기
    @GET("api/board/comment/")
    Call<CommentModel> getCommentAll ();

    //댓글 가져오기
    @GET("api/board/search/comment/{board_id}")
    Call<CommentModel> getComment (@Path( "board_id" ) String board_id);

    //댓글 쓰기
    @POST("api/board/comment/{board_id}")
    Call<ServerResponse> postComment (@FieldMap PostCommentModel commentModel);

}
