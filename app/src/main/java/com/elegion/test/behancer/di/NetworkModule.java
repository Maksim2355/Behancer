package com.elegion.test.behancer.di;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.data.api.ApiKeyInterceptor;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import toothpick.config.Module;

public class NetworkModule extends Module {

    private final Gson mGson = provideGson();
    private final OkHttpClient mOkHttpClient = provideOkHttpClient();
    private final Retrofit mRetrofit = provideRetrofit();
    private final BehanceApi mBehanceApi = provideBehanceApi();

    public NetworkModule(){
        bind(Gson.class).toInstance(provideGson());
        bind(Retrofit.class).toInstance(mRetrofit);
        bind(OkHttpClient.class).toInstance(mOkHttpClient);
        bind(BehanceApi.class).toInstance(mBehanceApi);
    }

    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(new ApiKeyInterceptor());
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return builder.build();
    }

    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    Gson provideGson() {
        return new Gson();
    }

    BehanceApi provideBehanceApi(){
        return mRetrofit.create(BehanceApi.class);
    }


}
