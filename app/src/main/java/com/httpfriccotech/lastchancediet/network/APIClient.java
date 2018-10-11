package com.httpfriccotech.lastchancediet.network;

import android.support.annotation.NonNull;

import com.httpfriccotech.lastchancediet.util.Global;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
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
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpSharedClient = new OkHttpClient.Builder()
                .cache(null)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request=chain.request().newBuilder().header("Cache-Control","no-cache").build();
                        return chain.proceed(request);
                    }
                })
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("Authorization",SharedPref.getToken(Global.getMyApplicationContext())).build();
                        return chain.proceed(request);
                    }
                })
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
