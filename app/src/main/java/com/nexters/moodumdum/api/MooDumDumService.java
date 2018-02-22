package com.nexters.moodumdum.api;

import com.nexters.moodumdum.model.ContentsModel;
import com.nexters.moodumdum.model.PostContentsModel;
import com.nexters.moodumdum.model.ServerResponse;

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

    public Call<ServerResponse> postContents(PostContentsModel contentsModel) {
        return  api.postContents(contentsModel);
    }

    public Call<ContentsModel> getContents() { return api.getContents();}
    public Call<ContentsModel> getMyContents(Long userId) {
        return  api.getMyContents(userId);
    }
    public Call<ContentsModel> getCategoryContentsInOrderOfPriority (String category_id) {
        return  api.getCategoryContentsInOrderOfPriority(category_id);
    }
}
