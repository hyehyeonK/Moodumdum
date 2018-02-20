package com.nexters.moodumdum.api;

import com.nexters.moodumdum.model.Contents;

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

    public Call<Contents> getContents(Long userId) {
        return  api.getContents(userId);
    }
}
