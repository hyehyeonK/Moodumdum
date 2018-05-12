package com.nexters.moodumdum.api;

import com.nexters.moodumdum.factory.RetrofitFactory;
import com.nexters.moodumdum.model.CategoryInfoModel;
import com.nexters.moodumdum.model.CommentModel;
import com.nexters.moodumdum.model.CommentUserModel;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.ImageModel;
import com.nexters.moodumdum.model.PutUserDataModel;
import com.nexters.moodumdum.model.ServerResponse;
import com.nexters.moodumdum.model.UserDataModel;

import java.math.BigInteger;

import retrofit2.Call;

/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public class MooDumDumService {
    private  MooDumDumAPI api;
    final static int limit = 10;
    private static class InstanceHolder {
        private static final MooDumDumService INSTANCE = new MooDumDumService();
    }

    private MooDumDumService() {
        api = RetrofitFactory.createMoodumdumRetrofit().create(MooDumDumAPI.class);
    }

    public static MooDumDumService of() { return  InstanceHolder.INSTANCE; }


    public Call<ServerResponse> postUserData(String uuid, String nickName) {
        return  api.postUserData(uuid, nickName);
    }
    public Call<ServerResponse> postContents(String uuid, BigInteger category_id, String description, String image_url, String font_color) {
        return  api.postContents(uuid, category_id, description, image_url, font_color);
    }
    public Call<ServerResponse> postDoLike(BigInteger board_id, String uuid) {
        return  api.postDoLike(uuid, board_id, uuid);
    }
    public Call<ContentsModel> getContents(String uuid) { return api.getContents(uuid);}
    public Call<ContentsModel> getMyContents(String userId, int offset) {
        return  api.getMyContents(userId,limit,offset);
    }
    public Call<ContentsModel> getMyJomunContents(String userId, int offset) {
        return  api.getMyJomunContents(userId, limit, offset);
    }
    public Call<ImageModel> getBackgroundImage() {return  api.getBackgroundImage();}
    public Call<ContentsModel.Result> getContentsSelected(String board_id, String uuid) { return api.getContentsSelected( board_id, uuid );}

    //카테고리 관련
    public Call<CategoryInfoModel> getCategoryInfo (String category_id) {
        return api.getCategoryInfo(category_id);
    }
    public Call<ContentsModel> getCategoryContentsInOrderOfPriority (String uuid, String category_id, int offset ) {
        return  api.getCategoryContentsInOrderOfPriority(category_id,limit, offset, uuid);
    }
    public Call<ContentsModel> getCategoryContentsInOrderOfPopularity (String uuid, String category_id, int offset) {
        return  api.getCategoryContentsInOrderOfPopularity(category_id,limit,offset, uuid);
    }

    //댓글 관련
//    public Call<CommentModel> getComment() {return api.getComment(String board_id);}

    public Call<CommentModel> getCommentAll() {return api.getCommentAll();}

//    public Call<CommentModel> getComment(BigInteger board_id) {
//        return api.getComment( board_id );
//    }
    public Call<CommentModel> getComment(BigInteger board_id, String user) {
        return api.getComment( board_id, user );
    }

    public Call<CommentUserModel> getCommentWithUser(String user_id) {return api.getCommentWithUser( user_id );}
    public Call<ServerResponse> postComment(String user, BigInteger board_id , String description) {
        return api.postComment( user, board_id, description );
    }
    public Call<ServerResponse> delComment(BigInteger id) {return api.delComment(id); }

    public Call<ServerResponse> postCommentLike(BigInteger comment_id, String user) {return api.postCommentLike( comment_id, user );
    }
    public Call<UserDataModel> getUserData(String user_id) {return api.getUserData( user_id );}
    public Call<PutUserDataModel> putUserData(String user_id, String user, String name, String profile_image) {return api.putUserData( user_id, user,name , profile_image);}

    //글 좋아요 취소
    public Call<ServerResponse> deleteContentsLike(String user_id, BigInteger board_id) {
        return api.deleteContentsLike(user_id, board_id);
    }

}
