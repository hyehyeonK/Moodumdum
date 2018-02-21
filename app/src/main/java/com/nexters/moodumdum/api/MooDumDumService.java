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

    private static class InsanceHolder {
        private static final MooDumDumService INSTANCE = new MooDumDumService();
    }

    private MooDumDumService() {
        api = RetrofitFactory.createMoodumdumRetrofit().create(MooDumDumAPI.class);
    }

    public static MooDumDumService of() { return  InsanceHolder.INSTANCE; }

    public Call<ServerResponse> postContents(PostContentsModel contents) {
        return  api.postContents(contents);
    }

    public Call<ContentsModel> getContents() { return api.getContents();}
    public Call<ContentsModel> getMyContents(Long userId) {
        return  api.getMyContents(userId);
    }
}
