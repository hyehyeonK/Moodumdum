package com.nexters.moodumdum.factory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public class RetrofitFactory {

    public static Retrofit createMoodumdumRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://13.125.76.112/".toString())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
