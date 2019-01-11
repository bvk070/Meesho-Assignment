package com.meesho.meeshoassignment.retrofit;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meesho.meeshoassignment.model.Comments;
import com.meesho.meeshoassignment.model.Commit;
import com.meesho.meeshoassignment.model.Commits;
import com.meesho.meeshoassignment.model.PullRequest;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.github.com/";

    private static RetrofitClient instance;
    private ApiServices apiServices;

    private RetrofitClient() {
        final Gson gson =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiServices = retrofit.create(ApiServices.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public Observable<List<PullRequest>> getPullRequests(@NonNull String userName, @NonNull String repo) {
        return apiServices.getPullRequests(userName,repo);
    }

    public Observable<List<Commit>> getCommits(@NonNull String userName, @NonNull String repo, @NonNull String number) {
        return apiServices.getCommits(userName,repo,number);
    }

}
