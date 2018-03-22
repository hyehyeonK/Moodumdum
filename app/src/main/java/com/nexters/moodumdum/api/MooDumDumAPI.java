package com.nexters.moodumdum.api;

import com.nexters.moodumdum.model.CategoryInfoModel;
import com.nexters.moodumdum.model.CommentModel;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.ImageModel;
import com.nexters.moodumdum.model.ServerResponse;
import com.nexters.moodumdum.model.UserModel;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public interface MooDumDumAPI {
    //글쓰기
    @FormUrlEncoded
    @POST("api/board/")
    Call<ServerResponse> postContents (@Field("category_id") BigInteger category_id,
                                       @Field("user") String user,
                                       @Field("name") String name,
                                       @Field("description") String description,
                                       @Field("image_url") String image_url,
                                       @Field("color") String font_color);
    //글가져오기
    @GET("api/board")
    Call<ContentsModel> getContents ();

    //배경사진 가져오기
    @GET("api/boardimage/random/?limit=20")
    Call<ImageModel> getBackgroundImage();

    //board_id로 글 가져오기
    @GET("api/board/{board_id}")
    Call<ContentsModel.Result> getContentsSelected(@Path( "board_id" )String board_id);

    //내가 쓴 글 가져오기
    @GET("api/board/search/user/{userId}")
    Call<ContentsModel> getMyContents (@Path("userId") String userId);

    //내가 좋아요 한 글 가져오기
    @GET("api/board/search/user/{userId}")
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
    Call<ServerResponse> postComment (@Field("board_id") BigInteger board_id,
                                      @Field( "user") String user,
                                      @Field( "name" ) String name,
                                      @Field( "description") String description);

    // 마이페이지의 좋아요 수, 게시글 수 가져오기
    @GET("api/user/{user_id}")
    Call<UserModel> getMypageCount (@Path( "user_id" ) String user);

}
