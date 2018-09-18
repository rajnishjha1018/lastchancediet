package com.httpfriccotech.lastchancediet.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class APIClient {
    private final long DEFAULT_READ_TIME_OUT = 40;
    private final long DEFAULT_CONNECT_TIME_OUT = 40;
    APIQueries apiQueries;
    private static APIClient mInstance;

    public static APIQueries startQuery() {
        if (mInstance == null) {
            mInstance = new APIClient();
        }
        return mInstance.apiQueries;
    }

    private APIClient() {
        OkHttpClient okHttpSharedClient = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_CONNECT_TIME_OUT, TimeUnit.SECONDS).build();

        Retrofit _retrofit = new Retrofit.Builder()
                .baseUrl(CommunicationConstants.BASE_URL_KNOWLAGE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpSharedClient)
                .build();

        apiQueries = _retrofit.create(APIQueries.class);
    }
}
