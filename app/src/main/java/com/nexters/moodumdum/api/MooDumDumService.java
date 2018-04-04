package com.nexters.moodumdum.api;

import com.nexters.moodumdum.factory.RetrofitFactory;
import com.nexters.moodumdum.model.CategoryInfoModel;
import com.nexters.moodumdum.model.CommentModel;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.ImageModel;
import com.nexters.moodumdum.model.ServerResponse;
import com.nexters.moodumdum.model.UserModel;

import java.math.BigInteger;

import retrofit2.Call;

/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public class MooDumDumService {
    private  MooDumDumAPI api;

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

    public Call<ContentsModel> getContents(String uuid) { return api.getContents(uuid);}
    public Call<ContentsModel> getMyContents(String userId) {
        return  api.getMyContents(userId);
    }
    public Call<ContentsModel> getMyJomunContents(String userId) {
        return  api.getMyJomunContents(userId);
    }
    public Call<ImageModel> getBackgroundImage() {return  api.getBackgroundImage();}
    public Call<ContentsModel.Result> getContentsSelected(String board_id) { return api.getContentsSelected( board_id );}

    //카테고리 관련
    public Call<CategoryInfoModel> getCategoryInfo (String category_id) {
        return api.getCategoryInfo(category_id);
    }
    public Call<ContentsModel> getCategoryContentsInOrderOfPriority (String category_id) {
        return  api.getCategoryContentsInOrderOfPriority(category_id);
    }
    public Call<ContentsModel> getCategoryContentsInOrderOfPopularity (String category_id) {
        return  api.getCategoryContentsInOrderOfPopularity(category_id);
    }

    //댓글 관련
//    public Call<CommentModel> getComment() {return api.getComment(String board_id);}

    public Call<CommentModel> getCommentAll() {return api.getCommentAll();}

    public Call<CommentModel> getComment(BigInteger board_id) {
        return api.getComment( board_id );
    }
    public Call<ServerResponse> postComment(BigInteger board_id,  String user, String name , String description) {
        return api.postComment(  board_id, user,  name ,  description );
    }
    public Call<UserModel> getMypageCount(String user_id) {return api.getMypageCount( user_id );}
}
