package com.nexters.moodumdum.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimhyehyeon on 2018. 2. 11..
 */

public class RetrofitFactory {
    public static Retrofit createReadRetrofit() {
        return new Retrofit.Builder()
//                .baseUrl(ReadApplication.globalApplicationContext.getText(R.string.base_url).toString())
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Retrofit createJusoRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://13.125.76.112:8000/")
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
