package com.zcy.ygs.retrofitdemo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ygs on 2018/5/5.
 */

public interface GithubClient{

    @GET("/users/{user}/repos")
    Call<List<GithubRepos>>  getReposCall(@Path("user") String path);

    @GET("/users/{user}/repos")
    Observable<List<GithubRepos>> getReposObser(@Path("user") String path);

}
