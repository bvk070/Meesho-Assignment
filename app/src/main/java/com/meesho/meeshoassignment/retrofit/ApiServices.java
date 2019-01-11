package com.meesho.meeshoassignment.retrofit;

import com.meesho.meeshoassignment.model.Commit;
import com.meesho.meeshoassignment.model.PullRequest;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiServices {

    @GET("repos/{user}/{repo}/pulls?client_id=be59d8cb17972bca2a83&client_secret=1c77db7c25d3eac7c1fc70de0c0f6bbc45375716")
    Observable<List<PullRequest>> getPullRequests(@Path("user") String userName, @Path("repo") String repo);

    @GET("repos/{user}/{repo}/pulls/{number}/commits?client_id=be59d8cb17972bca2a83&client_secret=1c77db7c25d3eac7c1fc70de0c0f6bbc45375716")
    Observable<List<Commit>> getCommits(@Path("user") String userName, @Path("repo") String repo, @Path("number") String number);

}


