package com.nexters.moodumdum.api;

import com.nexters.moodumdum.model.CommentModel;
import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.ImageModel;
import com.nexters.moodumdum.model.ServerResponse;

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

    public Call<ServerResponse> postContents(BigInteger category_id,  String user, String name , String description, String image_url) {
        return  api.postContents(category_id, user, name, description, image_url);
    }

    public Call<ContentsModel> getContents() { return api.getContents();}
    public Call<ContentsModel> getMyContents(Long userId) {
        return  api.getMyContents(userId);
    }
    public Call<ImageModel> getBackgroundImage() {return  api.getBackgroundImage();}
    public Call<ContentsModel.Result> getContentsSelected(String board_id) { return api.getContentsSelected( board_id );}
    public Call<ContentsModel> getCategoryContentsInOrderOfPriority (String category_id) {
        return  api.getCategoryContentsInOrderOfPriority(category_id);
    }
//    public Call<CommentModel> getComment() {return api.getComment(String board_id);}

    public Call<CommentModel> getCommentAll() {return api.getCommentAll();}

    public Call<CommentModel> getComment(BigInteger board_id) {
        return api.getComment( board_id );
    }
    public Call<ServerResponse> postComment(BigInteger board_id,  String user, String name , String description) {
        return api.postComment(  board_id, user,  name ,  description );
    }
}
