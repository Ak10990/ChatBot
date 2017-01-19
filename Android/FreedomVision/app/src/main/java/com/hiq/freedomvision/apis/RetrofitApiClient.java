package com.hiq.freedomvision.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiClient {

    /**
     * Api EndPoints
     */
    private static final String API_HOMEPAGE = "http://54.254.194.216:6800";

    private RetrofitApiService apiService;

    public RetrofitApiClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_HOMEPAGE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(RetrofitApiService.class);
    }

    public RetrofitApiService getApiService() {
        return apiService;
    }

}