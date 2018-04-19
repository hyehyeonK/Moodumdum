package com.nexters.moodumdum.api;

import com.nexters.moodumdum.model.CategoryInfoModel;
import com.nexters.moodumdum.model.CommentModel;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.ImageModel;
import com.nexters.moodumdum.model.PutUserDataModel;
import com.nexters.moodumdum.model.ServerResponse;
import com.nexters.moodumdum.model.UserDataModel;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public interface MooDumDumAPI {

    //user Data 저장
    @FormUrlEncoded
    @POST("api/user/")
    Call<ServerResponse> postUserData (@Field("user") String uuid,
                                      @Field("name") String nickName);
    //글쓰기
    @FormUrlEncoded
    @POST("api/board/")
    Call<ServerResponse> postContents (@Query( "user" ) String uuid,
                                       @Field("category_id") BigInteger category_id,
                                       @Field("description") String description,
                                       @Field("image_url") String image_url,
                                       @Field("color") String font_color);
    //글가져오기
    @GET("api/board/")
    Call<ContentsModel> getContents (@Query( "user" ) String uuid);

    //배경사진 가져오기
    @GET("api/boardimage/random/?limit=20")
    Call<ImageModel> getBackgroundImage();

    //board_id로 글 가져오기
    @GET("api/board/{board_id}")
    Call<ContentsModel.Result> getContentsSelected(@Path( "board_id" )String board_id);

    //내가 쓴 글 가져오기
    @GET("api/board/search/user/{userId}")
    Call<ContentsModel> getMyContents (@Path("userId") String userId);

    //좋아요 보내기
    @FormUrlEncoded
    @POST("api/board/like/")
    Call<ServerResponse> postDoLike ( @Query("user") String uuid ,
                                      @Field("board_id") BigInteger board_id,
                                      @Field("user") String uuid2);

    //내가 좋아요 한 글 가져오기
    @GET("api/board/user/like/{userId}")
    Call<ContentsModel> getMyJomunContents (@Path("userId") String userId);

    //카테고리 베너및 타이틀 가져오기
    @GET("api/board/category/{category_id}")
    Call<CategoryInfoModel> getCategoryInfo (@Path("category_id") String category_id);

   //카테고리별 컨텐츠 가져오기 ( 최신순 )
    @GET("api/board/search/category/{category_id}")
    Call<ContentsModel> getCategoryContentsInOrderOfPriority (@Path("category_id") String category_id);

    //카테고리별 컨텐츠 가져오기 ( 인기순 )
    @GET("api/board/search/category/favorite/{category_id}")
    Call<ContentsModel> getCategoryContentsInOrderOfPopularity (@Path("category_id") String category_id);

    //모든 댓글 가져오기
    @GET("api/board/comment/")
    Call<CommentModel> getCommentAll ();

    //댓글 가져오기
    @GET("api/board/search/comment/{board_id}")
    Call<CommentModel> getComment (@Path( "board_id" ) BigInteger board_id);

    //댓글 쓰기
    @FormUrlEncoded
    @POST("api/board/comment/")
    Call<ServerResponse> postComment (@Query( "user" ) String user,
                                      @Field( "board_id" ) BigInteger board_id,
                                      @Field( "description") String description);

    // 댓글 삭제
    @DELETE("api/board/comment/{id}/")
    Call<ServerResponse> delComment (@Path( "id") BigInteger id);

    // 댓글 좋아요
    @FormUrlEncoded
    @POST("api/board/comment/like/")
    Call<ServerResponse> postCommentLike(@Field( "comment_id" ) BigInteger comment_id,
                                         @Field( "user" ) String user);

    // User Data 가져오기
    @GET("api/user/info/{user_id}")
    Call<UserDataModel> getUserData (@Path( "user_id" ) String user);

    // User Data 수정하기
    @FormUrlEncoded
    @PUT("api/user/{user_id}/")
    Call<PutUserDataModel> putUserData (@Path( "user_id" )String user_id,
                                        @Field( "user" ) String user,
                                        @Field ("name") String name,
                                        @Field( "proflie_image" ) String profile_image);
}
